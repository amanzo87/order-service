package it.interno.order.client;

import it.interno.common.lib.model.OrderDto;
import it.interno.order.dto.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", path = "/payment")
public interface PaymentClient {

    @PostMapping(path = "/order")
    ResponseEntity<ResponseDto<OrderDto>> processaPagamentoOrdine(@RequestBody OrderDto orderDto);

    @PostMapping(path = "/fallimento-ordine")
    ResponseEntity<ResponseDto<String>> fallimentoOrdine(@RequestBody OrderDto orderDto);

}
