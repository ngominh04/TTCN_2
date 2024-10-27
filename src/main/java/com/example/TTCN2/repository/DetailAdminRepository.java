package com.example.TTCN2.repository;

import com.example.TTCN2.domain.DetailAdmin;
import com.example.TTCN2.sql.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailAdminRepository extends JpaRepository<DetailAdmin, Integer> {
    // lay detailAdmin theo idAdmin
    @Query(value = SQL.DETAIL_ADMIN_TO_IDADMIN,nativeQuery = true)
    DetailAdmin findDetailAdminById(Integer idAdmin);
}