package it.interno.order.repository;

import it.interno.order.entity.Order;
import it.interno.order.entity.key.OrderKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;

public interface OrderRepository extends JpaRepository<Order, OrderKey> {

    @Query(value =
            "SELECT COALESCE(MAX(ID_ORDER), 0) + 1 " +
            "FROM SHOP1.CUSTOMER_ORDER ", nativeQuery = true)
    Integer getMaxOrder() ;

    @Modifying
    @Query(value =
            "UPDATE SHOP1.CUSTOMER_ORDER " +
            "SET TS_CANCELLAZIONE = ?2 , " +
            "    ID_UTE_CAN = ?3 " +
            "WHERE" +
            "     ID_ORDER = ?1 " +
            " AND TS_CANCELLAZIONE IS NULL ", nativeQuery = true)
    void cancellazioneLogica(Integer idOrdine, Timestamp tmsp, String idUtente);

}
