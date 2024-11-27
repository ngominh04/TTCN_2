package com.example.TTCN2.projection;

public interface IOrder {
    Integer getId();
    Double getTotalMoney();
    Integer getTotalQuantity();
    Integer getIdRece();
    String getNameRece();
    String getAddressRece();
    String getCreateDate();
    Integer getCountOrder();
    Integer getStatus();
    Integer getDateOrder();
}
