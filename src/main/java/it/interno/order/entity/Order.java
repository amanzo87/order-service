package it.interno.order.entity;

import it.interno.order.entity.key.OrderKey;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(schema = "SHOP1", name = "CUSTOMER_ORDER")
@IdClass(OrderKey.class)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_ORDER", columnDefinition = "INTEGER", nullable = false)
    Integer idOrdine;

    @Id
    @Column(name = "TS_INSERIMENTO", columnDefinition = "TIMESTAMP", scale = 6, nullable = false)
    Timestamp tsInserimento;

    @Column(name = "DESC_ORDER", columnDefinition = "VARCHAR2", length = 100, nullable = false)
    String descrizioneOrdine;

    @Column(name = "ID_STATO", columnDefinition = "INTEGER", nullable = false)
    Integer idStato;

    @Column(name = "ID_UTE_INS", columnDefinition = "CHAR", length = 8, nullable = false)
    String idUteIns;

    @Column(name = "TS_CANCELLAZIONE", columnDefinition = "TIMESTAMP", scale = 6)
    Timestamp tsCancellazione;

    @Column(name = "ID_UTE_CAN", columnDefinition = "CHAR", length = 8)
    String idUteCan;

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

    public String getDescrizioneOrdine() {
        return descrizioneOrdine;
    }

    public void setDescrizioneOrdine(String descrizioneOrdine) {
        this.descrizioneOrdine = descrizioneOrdine;
    }

    public Integer getIdStato() {
        return idStato;
    }

    public void setIdStato(Integer idStato) {
        this.idStato = idStato;
    }

    public String getIdUteIns() {
        return idUteIns;
    }

    public void setIdUteIns(String idUteIns) {
        this.idUteIns = idUteIns;
    }

    public Timestamp getTsCancellazione() {
        return tsCancellazione;
    }

    public void setTsCancellazione(Timestamp tsCancellazione) {
        this.tsCancellazione = tsCancellazione;
    }

    public String getIdUteCan() {
        return idUteCan;
    }

    public void setIdUteCan(String idUteCan) {
        this.idUteCan = idUteCan;
    }
}
