package com.example.telecare.repository;


import com.example.telecare.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {
    @Query(value = "SELECT * FROM telecare.payment where trace = ?1"
            ,nativeQuery = true)
    Payment findPaymentByTrace(String trace);

    @Query(value = "SELECT * FROM telecare.payment where appointment_id = ?1 and status = ?2"
            ,nativeQuery = true)
    Payment findPaymentDetailByAppointmentId(int id,int status);
    @Query(value = "SELECT * FROM telecare.payment as p\n" +
            "where p.appointment_id like  %?2% or p.bankcode like %?2% or p.banktran_no like %?2% or p.cardtype like  %?2% or p.transaction_date like  %?2% or p.transaction_no like  %?2% or p.trace like  %?2% \n" +
            "order by p.transaction_date desc\n" +
            "limit ?1,10",nativeQuery = true)
    List<Payment> getAllPayment(int index, String searchText);
    @Query(value = "SELECT count(*) FROM telecare.payment as p\n" +
            "where p.appointment_id like %?1% or p.bankcode like %?1% or p.banktran_no like %?1% or p.cardtype like %?1% or p.transaction_date like %?1% or p.transaction_no like %?1% or p.trace like %?1% "
            ,nativeQuery = true)
    int getNumberOfPayment(String searchText);


}
