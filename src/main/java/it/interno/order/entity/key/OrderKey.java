package it.interno.order.entity.key;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class OrderKey implements Serializable {

    Integer idOrdine;
    Timestamp tsInserimento;

    public Integer getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(Integer idOrdine) {
        this.idOrdine = idOrdine;
    }

    public Timestamp getTsInserimento() {
        return tsInserimento;
    }

    public void setTsInserimento(Timestamp tsInserimento) {
        this.tsInserimento = tsInserimento;
    }
}
