package com.example.telecare.repository;

import com.example.telecare.model.AppointmentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentDetailRepository extends JpaRepository<AppointmentDetails,Integer> {
}
