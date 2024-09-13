package com.example.TTCN2.repository;

import com.example.TTCN2.domain.OrderTransport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderTransportRepository extends JpaRepository<OrderTransport, Integer> {
}