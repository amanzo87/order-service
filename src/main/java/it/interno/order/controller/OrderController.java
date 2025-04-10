package it.interno.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import it.interno.common.lib.StatoOrdine;
import it.interno.common.lib.model.OrderDto;
import it.interno.order.dto.ResponseDto;
import it.interno.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Inserimento Ordine")
    @PostMapping(path = "/insert")
    public ResponseEntity<ResponseDto> inserimentoNuovoOrdine(@RequestBody OrderDto orderDto) {

        OrderDto ordine = orderService.inserimentoOrdine(orderDto);

        ResponseDto<OrderDto> response = null;

        if(StatoOrdine.ORDINE_CONFERMATO.getCodice() == ordine.getIdStato()) {
            response = new ResponseDto<>(HttpStatus.OK.value(), ordine, null, null);
        }else if(StatoOrdine.ORDINE_ANNULLATO.getCodice() == ordine.getIdStato()) {
            response = new ResponseDto<>(HttpStatus.FAILED_DEPENDENCY.value(), null, "Errore nella richiesta di creazione dell'ordine", null);
        }

        return ResponseEntity.ok(response);
    }

}
