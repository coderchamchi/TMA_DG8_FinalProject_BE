package com.bezkoder.springjwt.repository;

import java.util.List;
import java.util.Optional;

import com.bezkoder.springjwt.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bezkoder.springjwt.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  Optional<User> findByEmail(String email);

  Boolean existsByPassword(String password);

  User findUserByUsername(String username);

  @Query(
          value = "select full_name from user where status = 1",
          nativeQuery = true
  )
  List<String> GetAllUsername();

  @Query(
          value = "select email from user where status = 1",
          nativeQuery = true
  )
    List<String> GetAllEmail();

  @Query(
          value = "select password from user where status = 1",
          nativeQuery = true
  )
  List<String> GetAllPassword();
}
