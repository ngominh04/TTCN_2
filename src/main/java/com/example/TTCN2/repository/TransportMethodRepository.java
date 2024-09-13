package com.example.TTCN2.repository;

import com.example.TTCN2.domain.TransportMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportMethodRepository extends JpaRepository<TransportMethod, Integer> {
}