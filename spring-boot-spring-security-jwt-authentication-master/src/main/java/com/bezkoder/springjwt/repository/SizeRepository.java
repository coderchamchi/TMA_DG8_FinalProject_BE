package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.dto.SizeInCartDTO;
import com.bezkoder.springjwt.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {
    Optional<Size> findBySizeName(ESize sizeName);

    @Query(
            value = "SELECT * FROM size s WHERE s.id_product = :id",
            nativeQuery = true)
    List<Size> findByIdProduct(long id);

}
