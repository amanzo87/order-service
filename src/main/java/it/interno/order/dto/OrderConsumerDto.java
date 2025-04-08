package it.interno.order.dto;

import it.interno.common.lib.model.ProductDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

//@Data
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class OrderConsumerDto {

    Integer idOrdine;
    String descrizioneOrdine;
    String idCategoriaOrdine;
    Integer idStato;
    Boolean pagamentoEffettuato = Boolean.FALSE;
    List<ProductDto> elencoProdotti;
    String tsInserimento;
    String idUteIns;
}
