package com.example.TTCN2.repository;

import com.example.TTCN2.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    // lay toan bo cart
    @Query(value = "select * from cart ",nativeQuery = true)
    List<Cart> getAllCart();

    // lay cart theo isUser
    @Query(value = "select * from cart where id_user=?",nativeQuery = true)
    Cart findByIdUser(Integer idUser);
}