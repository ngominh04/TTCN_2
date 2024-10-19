package com.example.TTCN2.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "order_payment")
public class OrderPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

//    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_order")
    private Integer idOrder;

    @Column(name = "name_payment", length = 150)
    private String namePayment;

    @Column(name = "money")
    private Double money;

}