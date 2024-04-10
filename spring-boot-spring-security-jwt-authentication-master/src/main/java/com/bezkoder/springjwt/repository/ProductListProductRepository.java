package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductListProductRepository extends JpaRepository<Product, Long> {

}
