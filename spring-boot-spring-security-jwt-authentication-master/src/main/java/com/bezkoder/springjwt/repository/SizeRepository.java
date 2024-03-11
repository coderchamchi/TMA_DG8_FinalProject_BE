package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.entities.ERole;
import com.bezkoder.springjwt.entities.ESize;
import com.bezkoder.springjwt.entities.Role;
import com.bezkoder.springjwt.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SizeRepository extends JpaRepository<Size, Integer> {
    Optional<Size> findBySizeName(ESize sizeName);
}
