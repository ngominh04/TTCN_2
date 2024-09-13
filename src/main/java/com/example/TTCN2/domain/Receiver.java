package com.example.TTCN2.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "receiver")
public class Receiver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User idUser;

    @Column(name = "name", length = 150)
    private String name;

    @Column(name = "address", length = 250)
    private String address;

    @Column(name = "phone", length = 10)
    private String phone;

    @Column(name = "is_delete")
    private Integer isDelete;

}