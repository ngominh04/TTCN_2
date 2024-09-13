package com.example.TTCN2.repository;

import com.example.TTCN2.domain.ShipperOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipperOrderRepository extends JpaRepository<ShipperOrder, Integer> {
}