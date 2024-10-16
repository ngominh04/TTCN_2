package com.example.TTCN2.repository;

import com.example.TTCN2.domain.TransportMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransportMethodRepository extends JpaRepository<TransportMethod, Integer> {
    @Query(value = "select * from transport_method where is_active = 0 ",nativeQuery = true)
    List<TransportMethod> getAllById();
}