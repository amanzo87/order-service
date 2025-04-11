package it.interno.order.client;

import it.interno.common.lib.model.OrderDto;
import it.interno.order.dto.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "shipping-service", path = "/shipping")
public interface ShippingClient {

    @PostMapping(path = "/genera")
    ResponseEntity<ResponseDto<OrderDto>> generaSpedizione(@RequestBody OrderDto orderDto) ;

}
