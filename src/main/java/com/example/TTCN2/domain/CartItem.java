package com.example.TTCN2.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cart_item")
public class CartItem {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "id_cart")
    private Integer idCart;

    @Column(name = "name_tree", length = 150)
    private String nameTree;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "image")
    private String image;

    @Column(name = "money")
    private Double money;

//    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tree")
    private Integer idTree;

}