package com.example.TTCN2.repository;

import com.example.TTCN2.domain.DetailAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailAdminRepository extends JpaRepository<DetailAdmin, Integer> {
}