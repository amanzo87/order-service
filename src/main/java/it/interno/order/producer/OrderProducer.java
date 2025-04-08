//package it.interno.order.producer;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import it.interno.common.lib.model.OrderDto;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.StandardOpenOption;
//
//@Service
//@Slf4j
//public class OrderProducer {
//
//    private static final String TOPIC = "order-send";
//
//    private final KafkaTemplate<String, OrderDto> kafkaTemplate;
//
//    public OrderProducer(KafkaTemplate<String, OrderDto> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//
//    public void sendOrder(OrderDto orderDto) {
//
//        try{
//
//            orderDto.setIdStato(1); //"ORDINE IN ATTESA DI VERIFICA PRESENZA PRODOTTO IN MAGAZZINO");
//
//            kafkaTemplate.send(TOPIC, orderDto);
//
//            ObjectMapper objectMapper = new ObjectMapper();
//
//            String json = objectMapper.writeValueAsString(orderDto);
//            Files.write(Path.of("C:/temp/order.json"), (json + "\n").getBytes(),
//                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }
//
//}
