package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(
            value = "SELECT * FROM product u WHERE u.id_category = :id",
            nativeQuery = true)
    ArrayList<Product> findProductByStatusNative(Long id);


    @Query("SELECT p FROM Product p WHERE p.productName LIKE CONCAT('%',:query, '%')")
    ArrayList<Product> findProductbyname(String query);

    @Query(
            value = "select * from product where status = 1",
            nativeQuery = true
    )
    List<Product> GetAllProduct();

    @Query(
            value = "select * from product where product_name like CONCAT ('%',:name,'%')",
            nativeQuery = true
    )
    List<Product> GetProductRelation(String name);

    @Query(
            value = "select * from product where id_category = :categoryId",
            nativeQuery = true
    )
    List<Product> GetProductByCategory(long categoryId);
}

