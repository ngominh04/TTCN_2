package com.example.TTCN2.repository;

import com.example.TTCN2.domain.Evaluate;
import lombok.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluateRepository extends JpaRepository<Evaluate, Integer> {
    // evaluate by isOrderDetail
    @Query(value = "select * from evaluate where id_order_detail=?", nativeQuery = true)
    Evaluate findEvaluateByIdOrderDetail(Integer idOrderDetail);

}