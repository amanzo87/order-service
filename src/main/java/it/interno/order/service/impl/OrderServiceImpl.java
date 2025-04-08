package it.interno.order.service.impl;

import it.interno.common.lib.StatoOrdine;
import it.interno.common.lib.model.OrderDto;
import it.interno.common.lib.util.Utility;
import it.interno.order.entity.Order;
import it.interno.order.entity.key.OrderKey;
import it.interno.order.mapper.OrderMapper;
import it.interno.order.repository.OrderRepository;
import it.interno.order.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private static final String TOPIC = "order-send";

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Override
    @Transactional
    public OrderDto inserimentoOrdine(OrderDto orderDto) {

        try{

            orderDto.setIdOrdine( orderRepository.getMaxOrder() );
            orderDto.setTsInserimento( Utility.convertTimestampToString(Utility.getTimestamp()) );
            orderDto.setIdStato(1);

            Order entity = orderMapper.toEntity(orderDto);
            orderRepository.save( entity );

            kafkaTemplate.send(TOPIC, orderDto);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return orderDto;
    }

    @Override
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

//            kafkaTemplate.send(TOPIC, orderDto);

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
