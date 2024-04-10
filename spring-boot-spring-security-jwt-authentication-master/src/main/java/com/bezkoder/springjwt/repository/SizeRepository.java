package com.bezkoder.springjwt.repository;

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

    ArrayList<Size> findBySizeNameOrderBySizeName(long IdProduct);
}
