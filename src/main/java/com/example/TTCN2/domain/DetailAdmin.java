package com.example.TTCN2.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "detail_admin")
public class DetailAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_admin")
    private Admin idAdmin;

    @Column(name = "name", length = 150)
    private String name;

    @Column(name = "phone", length = 10)
    private String phone;

    @Column(name = "address", length = 250)
    private String address;

    @Column(name = "id_card", length = 12)
    private String idCard;

    @Column(name = "image", length = 250)
    private String image;

    @Column(name = "create_date")
    private String createDate;

    @Column(name = "update_date")
    private String updateDate;

}