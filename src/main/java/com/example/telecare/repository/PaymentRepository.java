package com.example.telecare.repository;


import com.example.telecare.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {
    @Query(value = "SELECT \n" +
            "case\n" +
            "when count( trace) = 0 then \"0\"\n" +
            "else trace\n" +
            "end as trace\n" +
            "FROM telecare.payment ORDER BY ID DESC LIMIT 1"
            ,nativeQuery = true)
    String getLastTractNumber();
    @Query(value = "SELECT * FROM telecare.payment where trace = ?1"
            ,nativeQuery = true)
    Payment findPaymentByTrace(String trace);
}
