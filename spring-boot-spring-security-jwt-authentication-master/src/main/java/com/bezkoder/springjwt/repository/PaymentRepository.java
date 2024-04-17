package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query(
            value = "select * from payment where id_shopping_cart = :id",
            nativeQuery = true
    )
    Payment GetPayment(long id);
}
