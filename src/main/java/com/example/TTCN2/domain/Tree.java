package com.example.TTCN2.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "trees")
public class Tree {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_category")
    private int idCategory;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "money")
    private Double money;

    @Column(name = "create_date")
    private String createDate;

    @Column(name = "update_date")
    private String updateDate;

    @Column(name = "repairer")
    private Integer repairer;

    @Column(name = "is_active")
    private Integer isActive;

    @Column(name = "is_delete")
    private Integer isDelete;

    @Column(name = "notes")
    private String notes;

}