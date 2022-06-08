package com.example.telecare.repository;

import com.example.telecare.model.Relative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RelativeRepository extends JpaRepository<Relative, Integer> {

    @Query(value = "SELECT * from relative where patient_id = ?1", nativeQuery = true)
    List<Relative> findRelativesByPatientId(int id);
}
