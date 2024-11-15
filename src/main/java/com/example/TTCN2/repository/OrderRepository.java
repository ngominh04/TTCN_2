package com.example.TTCN2.repository;

import com.example.TTCN2.domain.Order;
import com.example.TTCN2.projection.IOrder;
import com.example.TTCN2.sql.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    // order the idCus , status
    @Query(value = SQL.ORDER_BY_IDCUS_AND_STATUS,nativeQuery = true)
    List<Order> findByCusAndStatus(Integer idCus, Integer status);

    // order by idOrder
    @Query(value = SQL.ORDER_BY_IDORDER,nativeQuery = true)
    Order findByIdOrder(Integer idOrder);
    // count order
    @Query(value = SQL.COUNT_ORDER,nativeQuery = true)
    IOrder countAllById();
    // limit 5 desc receiver_date
    @Query(value = SQL.LIMIT_ORDER_BY_RECEIVER_DATE,nativeQuery = true)
    List<Order> findOrderByReceiverDate();
    // order the idCus , status
    @Query(value = SQL.ORDER_BY_STATUS,nativeQuery = true)
    List<Order> getAllOrderByStatus(Integer status);
    // count order by status
    @Query(value = SQL.COUNT_ORDER_BY_STATUS,nativeQuery = true)
    IOrder countAllByStatus(Integer status);
}