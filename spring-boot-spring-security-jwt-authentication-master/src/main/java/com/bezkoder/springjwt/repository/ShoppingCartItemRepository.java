package com.bezkoder.springjwt.repository;


import com.bezkoder.springjwt.dto.SizeInCartDTO;
import com.bezkoder.springjwt.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, Long> {
    @Query(
            value = "SELECT * FROM shopping_cart_item JOIN shopping_cart ON shopping_cart_item.id_shopping_cart = shopping_cart.id_shopping_cart where shopping_cart.id_shopping_cart = :id",
            nativeQuery = true
    )
    List<ShoppingCartItem> gelAllItem(long id);

    @Query(
            value = "SELECT s.id_size, s.size_name, s.price FROM shopping_cart_item i JOIN size s ON s.id_size = i.id_size where i.id_shopping_cart_item = :id",
            nativeQuery = true
    )
    List<SizeInCartDTO> findByShoppingCartItem(long id);
}
