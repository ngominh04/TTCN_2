package com.example.TTCN2.repository;

import com.example.TTCN2.domain.Order;
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
}