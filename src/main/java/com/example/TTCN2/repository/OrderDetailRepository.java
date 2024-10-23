package com.example.TTCN2.repository;

import com.example.TTCN2.domain.OrderDetail;
import com.example.TTCN2.sql.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    // orderDetail by idOrder
    @Query(value = SQL.ORDERDETAIL_BY_IDORDER,nativeQuery = true)
    List<OrderDetail> findByOrderId(Integer idOrder);
}