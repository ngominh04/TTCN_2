package com.example.TTCN2.repository;

import com.example.TTCN2.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    @Query(value = "select * from cart_item where id_cart=?",nativeQuery = true)
    List<CartItem> getAllCartItem_idCart(Integer idCart);
}