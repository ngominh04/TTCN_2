package com.example.TTCN2.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "evaluate")
public class Evaluate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_order_detail")
    private OrderDetail idOrderDetail;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "username_cus", length = 150)
    private String usernameCus;

    @Column(name = "evaluate_date")
    private Instant evaluateDate;

}