package it.interno.order.service;

import it.interno.common.lib.model.OrderDto;

public interface OrderService {

    OrderDto inserimentoOrdine(OrderDto orderDto);

    OrderDto aggiornamentoOrdine(OrderDto orderDto);

    OrderDto test(OrderDto orderDto);

}
