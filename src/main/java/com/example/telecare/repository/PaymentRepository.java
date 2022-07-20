package com.example.telecare.repository;


import com.example.telecare.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {
    @Query(value = "SELECT * FROM telecare.payment where trace = ?1"
            ,nativeQuery = true)
    Payment findPaymentByTrace(String trace);
}
