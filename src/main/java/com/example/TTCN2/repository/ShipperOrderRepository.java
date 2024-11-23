package com.example.TTCN2.repository;

import com.example.TTCN2.domain.Shipper;
import com.example.TTCN2.domain.ShipperOrder;
import com.example.TTCN2.sql.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipperOrderRepository extends JpaRepository<ShipperOrder, Integer> {
    // get shipper_order by idOrder
    @Query(value = SQL.GET_SHIPPER_ORDER_BY_IDORDER,nativeQuery = true)
    ShipperOrder findByOrderId(Integer idOrder);

    // get all shipperOrder by idShipper
    @Query(value = SQL.GET_ALL_SHIPPER_ORDER_BY_IDSHIPPER,nativeQuery = true)
    List<ShipperOrder> getAllByShipperId(Integer idShipper);

    // get shipper by idShipper
    @Query(value = SQL.GET_SHIPPER_BY_IDSHIPPER,nativeQuery = true)
    Shipper showShipperById(Integer idShipper);
}