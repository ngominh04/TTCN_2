package com.example.TTCN2.repository;

import com.example.TTCN2.domain.Shipper;
import com.example.TTCN2.domain.User;
import com.example.TTCN2.projection.IShipper;
import com.example.TTCN2.sql.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipperRepository extends JpaRepository<Shipper, Integer> {
    // count shipper
    @Query(value = SQL.COUNT_SHIPPER,nativeQuery = true)
    IShipper countAllById();
    // lay toan bo user
    @Query(value = SQL.ALL_SHIPPER,nativeQuery = true)
    List<Shipper> getAll();

    // get shipper by username
    @Query(value = SQL.CHECK_LOGIN_SHIPPER,nativeQuery = true)
    Shipper checkLogin(String username);
}