package com.example.TTCN2.repository;

import com.example.TTCN2.domain.CartItem;
import com.example.TTCN2.projection.ICartItem;
import com.example.TTCN2.sql.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    @Query(value = "select * from cart_item where id_cart=?",nativeQuery = true)
    List<CartItem> getAllCartItem_idCart(Integer idCart);
    // dem so luong cartItem
    @Query(value = SQL.CART_ITEM_COUNT,nativeQuery = true)
    ICartItem getCartItemCount(Integer idCart);
    // lay cartItem theo idCartItem
    @Query(value = SQL.GET_CART_ITEM_BY_ID,nativeQuery = true)
    CartItem getCartItemById(Integer idCartItem);
}