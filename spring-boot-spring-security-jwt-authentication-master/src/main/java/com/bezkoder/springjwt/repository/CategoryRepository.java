package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>
{
    @Query(
            value = "SELECT * FROM category WHERE category_name LIKE CONCAT('%',:name, '%')",
            nativeQuery = true
    )
    Category getCategoryByName(String name);
}
