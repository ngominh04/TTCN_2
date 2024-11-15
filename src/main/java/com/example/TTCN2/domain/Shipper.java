package com.example.TTCN2.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "shipper")
public class Shipper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

//    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_role")
    private Integer idRole;

    @Column(name = "username", length = 150)
    private String username;

    @Column(name = "password", length = 250)
    private String password;

    @Column(name = "name", length = 150)
    private String name;

    @Column(name = "address", length = 250)
    private String address;

    @Column(name = "phone", length = 10)
    private String phone;

    @Column(name = "create_date")
    private String createDate;

    @Column(name = "update_date")
    private String updateDate;
//    Status ở shipper: 1 (đang rảnh),2 (đang giao sp)
    @Column(name = "status")
    private Integer status;

    @Column(name = "block")
    private Integer block;

}