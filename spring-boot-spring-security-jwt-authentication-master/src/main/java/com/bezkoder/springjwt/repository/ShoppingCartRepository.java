package com.bezkoder.springjwt.repository;


import com.bezkoder.springjwt.entities.ShoppingCart;
import com.bezkoder.springjwt.entities.ShoppingCartItem;
import com.bezkoder.springjwt.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

//    @Query(
//        value = "SELECT id_shopping_cart FROM shopping_cart s WHERE s.user_id = :id",
//        nativeQuery = true)
//    ShoppingCart shoppingCart (User user);
}
