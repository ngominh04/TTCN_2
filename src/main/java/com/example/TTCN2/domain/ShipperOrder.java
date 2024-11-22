package com.example.TTCN2.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "shipper_order")
public class ShipperOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

//    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_shipper")
    private Integer idShipper;

//    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_order")
    private Integer idOrder;

    @Column(name = "receiver_date")
    private String receiverDate;

    @Column(name = "delivery_date")
    private String deliveryDate;

    @Column(name = "image_order", length = 250)
    private String imageOrder;

    @Column(name = "status")
    private Integer status;

}