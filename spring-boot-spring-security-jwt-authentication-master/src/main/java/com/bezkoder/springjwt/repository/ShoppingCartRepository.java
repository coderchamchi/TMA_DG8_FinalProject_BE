package com.bezkoder.springjwt.repository;


import com.bezkoder.springjwt.entities.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    @Query(
            value = "select * from shopping_cart where status = 1 and id_user = :user",
            nativeQuery = true
    )
    ShoppingCart getShoppingCartByUser(long user);

}
