//package it.interno.order.client.fallback;
//
//import it.interno.common.lib.model.OrderDto;
//import it.interno.order.client.InventoryClient;
//import it.interno.order.dto.ResponseDto;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
//
//public class InventoryServiceFallback implements InventoryClient {
//
//    @Override
//    public ResponseEntity<ResponseDto> verificaDisponibilitaProdotti(@RequestHeader String idUtente, @RequestBody OrderDto orderDto) {
//
//
//
//        ResponseDto<Boolean> responseDto = new ResponseDto<>(
//                HttpStatus.SERVICE_UNAVAILABLE.value(),
//                false, "Servizio non disponibile", null);
//        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(responseDto);
//    }
//
//    @Override
//    public ResponseEntity<ResponseDto> fallimentoOrdine(String idUtente, OrderDto orderDto) {
//
//
//
//        ResponseDto<Boolean> responseDto = new ResponseDto<>(
//                HttpStatus.SERVICE_UNAVAILABLE.value(),
//                false, "Servizio non disponibile", null);
//
//        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(responseDto);
//    }
//}
