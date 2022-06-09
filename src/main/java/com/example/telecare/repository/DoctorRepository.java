package com.example.telecare.repository;

import com.example.telecare.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository  extends JpaRepository<Doctor,Integer> {
}
