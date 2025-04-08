package it.interno.order.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.interno.common.lib.model.OrderDto;
import it.interno.common.lib.model.OrderFailedEvent;
import it.interno.common.lib.model.OrderSuccessEvent;
import it.interno.order.service.OrderService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    @Autowired
    private OrderService orderService;

    @KafkaListener(topics = "order-receive", groupId = "order-group")
    public void consumeOrderReceive(ConsumerRecord<String, OrderDto> element) {

        OrderDto orderDto = element.value();

        try{
            ObjectMapper objectMapper = new ObjectMapper();

            if(orderDto instanceof OrderSuccessEvent) {
                orderDto.setIdStato(4);
            }else if(orderDto instanceof OrderFailedEvent) {
                orderDto.setIdStato(7);
            }

            orderService.aggiornamentoOrdine(orderDto);

//            String json = objectMapper.writeValueAsString(orderDto);
//            Files.write(Path.of("C:/temp/order.json"), (json + "\n").getBytes(),
//                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        }

    }
}
