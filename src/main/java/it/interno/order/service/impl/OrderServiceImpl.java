package it.interno.order.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.interno.common.lib.StatoOrdine;
import it.interno.common.lib.model.OrderDto;
import it.interno.common.lib.util.Utility;
import it.interno.order.client.InventoryClient;
import it.interno.order.client.PaymentClient;
import it.interno.order.client.ShippingClient;
import it.interno.order.dto.ResponseDto;
import it.interno.order.entity.Order;
import it.interno.order.entity.key.OrderKey;
import it.interno.order.mapper.OrderMapper;
import it.interno.order.repository.OrderRepository;
import it.interno.order.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {

    private static final String TOPIC = "order-send";

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private InventoryClient inventoryClient;

    @Autowired
    private PaymentClient paymentClient;

    @Autowired
    private ShippingClient shippingClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    //@Transactional
    public OrderDto inserimentoOrdine(OrderDto orderDto) {

        OrderDto dto = null ;

        try{

            dto = inserimento(orderDto);

            ResponseEntity<ResponseDto<OrderDto>> inventoryResponse =
                    inventoryClient.verificaDisponibilitaProdotti(dto.getIdUteIns(), dto);

            if(inventoryResponse.getBody() != null &&
                    HttpStatus.OK.value() == inventoryResponse.getBody().getCode()) {

                OrderDto dtoInventoryResponse = objectMapper.convertValue(
                        inventoryResponse.getBody().getBody(), OrderDto.class);

                ResponseEntity<ResponseDto<OrderDto>> paymentResponse =
                        paymentClient.processaPagamentoOrdine(dtoInventoryResponse);

                if(paymentResponse.getBody() != null) {
                    OrderDto paymentResponseDto = objectMapper.convertValue(
                            paymentResponse.getBody().getBody(), OrderDto.class);

                    if(paymentResponseDto.getPagamentoEffettuato()) {

                        ResponseEntity<ResponseDto<OrderDto>> shippingResponse =
                                shippingClient.generaSpedizione(paymentResponseDto);

                        if(shippingResponse.getBody() != null && shippingResponse.getBody().getBody() != null) {
                            OrderDto shippingResponseDto = objectMapper.convertValue(
                                    shippingResponse.getBody().getBody(), OrderDto.class);
                            dto.setIdStato(StatoOrdine.ORDINE_CONFERMATO.getCodice());
                        }else{

                            // CallBack Inventory
                            callBackInventory(paymentResponseDto);

                            // CallBack Payment
                            callBackPayment(paymentResponseDto);

                            dto.setIdStato(StatoOrdine.ORDINE_ANNULLATO.getCodice());
                        }
                    }else{
                        dto.setIdStato(StatoOrdine.ORDINE_ANNULLATO.getCodice());

                        // CallBack Inventory
                        callBackInventory(paymentResponseDto);
                    }
                }else{
                    dto.setIdStato(StatoOrdine.ORDINE_ANNULLATO.getCodice());
                }
            }else{
                dto.setIdStato(StatoOrdine.ORDINE_ANNULLATO.getCodice());
            }

            aggiornamentoOrdine(dto);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return dto;
    }

    private void callBackInventory(OrderDto paymentResponseDto) {
        ResponseEntity<ResponseDto<String>> inventoryCallbackResponse =
                inventoryClient.fallimentoOrdine(paymentResponseDto.getIdUteIns(), paymentResponseDto);

        if(inventoryCallbackResponse.getBody() != null) {
            if(!"CANCELLAZIONE EFFETTUATA".equals(objectMapper.convertValue(
                    inventoryCallbackResponse .getBody().getBody(), String.class))) {
                System.out.println("PROBLEMA CON CALLBACK INVENTORY - ALIMENTARE TABELLA XXX");
            }
        }
    }

    private void callBackPayment(OrderDto orderDto) {
        ResponseEntity<ResponseDto<String>> paymentCallbackResponse =
                paymentClient.fallimentoOrdine(orderDto);

        if(paymentCallbackResponse.getBody() != null) {
            if(!"CANCELLAZIONE EFFETTUATA".equals(objectMapper.convertValue(
                    paymentCallbackResponse .getBody().getBody(), String.class))) {
                System.out.println("PROBLEMA CON CALLBACK PAYMENT - ALIMENTARE TABELLA XXX");
            }
        }
    }

    @Transactional
    private OrderDto inserimento(OrderDto orderDto) {
        orderDto.setIdOrdine( orderRepository.getMaxOrder() );
        orderDto.setTsInserimento( Utility.convertTimestampToString(Utility.getTimestamp()) );
        orderDto.setIdStato(1);

        Order entity = orderMapper.toEntity(orderDto);
        orderRepository.save( entity );

        return orderDto;
    }

    //@Override
    @Transactional
    public OrderDto aggiornamentoOrdine(OrderDto orderDto) {

        try{

            String tmsp = Utility.convertTimestampToString(Utility.getTimestamp());

            if(StatoOrdine.ORDINE_CONFERMATO.getCodice() == orderDto.getIdStato()) {

                orderRepository.cancellazioneLogica(orderDto.getIdOrdine(),
                        Utility.convertStringToTimestamp(tmsp), orderDto.getIdUteIns());

                orderDto.setTsInserimento(tmsp);

                Order entity = orderMapper.toEntity(orderDto);
                orderRepository.save( entity );

            }else if(StatoOrdine.ORDINE_ANNULLATO.getCodice() == orderDto.getIdStato()) {
                OrderKey orderKey = new OrderKey();
                orderKey.setIdOrdine(orderDto.getIdOrdine());
                orderKey.setTsInserimento(Utility.convertStringToTimestamp(orderDto.getTsInserimento()));
                orderRepository.deleteById(orderKey);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return orderDto;
    }

    public OrderDto test(OrderDto orderDto) {
        return orderDto;
    }

}
