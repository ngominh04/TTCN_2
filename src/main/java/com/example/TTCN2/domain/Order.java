package com.example.TTCN2.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

//    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private Integer idUser;

    @Column(name = "total_money")
    private Double totalMoney;

    @Column(name = "total_quantity")
    private Integer totalQuantity;

//    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_transport")
    private Integer idTransport;

//    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_payment")
    private Integer idPayment;

    @Column(name = "id_receiver")
    private Integer idReceiver;

    @Column(name = "create_date")
    private String createDate;

    @Column(name = "receiver_date")
    private String receiverDate;

    @Column(name = "status")
    private Integer status;

}