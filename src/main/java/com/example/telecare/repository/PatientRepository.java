package com.example.telecare.repository;

import com.example.telecare.model.Patient;
import com.example.telecare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PatientRepository extends JpaRepository<Patient,Integer> {
//    @Query(value = "INSERT INTO telecare.patient values (?1,NULL,NULL,NULL,NULL,NULL,NULL)",
//            nativeQuery = true)
//    void createNewPatient(int uid);
}
