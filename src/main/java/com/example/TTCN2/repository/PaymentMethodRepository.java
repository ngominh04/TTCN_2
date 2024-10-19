package com.example.TTCN2.repository;

import com.example.TTCN2.domain.PaymentMethod;
import com.example.TTCN2.domain.TransportMethod;
import com.example.TTCN2.sql.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {
    // get all
    @Query(value = "select * from payment_method where is_active = 0 ",nativeQuery = true)
    List<PaymentMethod> getAllById();
    // lay payment theo idPay
    @Query(value = SQL.GET_PAYMENT_BY_IDPAY,nativeQuery = true)
    PaymentMethod getPaymentById(Integer idPay);
}