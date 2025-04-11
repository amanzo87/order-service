package it.interno.order.client;

import it.interno.common.lib.model.OrderDto;
import it.interno.order.dto.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "inventory-service", path = "/inventory") //, fallback = InventoryServiceFallback.class)
public interface InventoryClient {

    @PostMapping(path = "/verifica-disponibilita-prodotti")
    ResponseEntity<ResponseDto<OrderDto>> verificaDisponibilitaProdotti(@RequestHeader String idUtente, @RequestBody OrderDto orderDto);

    @PostMapping(path = "/fallimento-ordine")
    ResponseEntity<ResponseDto<String>> fallimentoOrdine(@RequestHeader String idUtente, @RequestBody OrderDto orderDto);

}
