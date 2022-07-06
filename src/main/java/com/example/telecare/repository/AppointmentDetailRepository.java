package com.example.telecare.repository;

import com.example.telecare.model.AppointmentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AppointmentDetailRepository extends JpaRepository<AppointmentDetails,Integer> {
    @Query(value = "SELECT * from appointment_details where appointment_id = ?1", nativeQuery = true)
    AppointmentDetails findAppointmentDetailsByAppointmentId(int id);
}
