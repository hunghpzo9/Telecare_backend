package com.example.telecare.repository;

import com.example.telecare.model.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpecialtyRepository extends JpaRepository<Specialty,Integer> {
    @Query(value = "SELECT s.id,s.name FROM telecare.specialty s left outer join telecare.doctor_specialty ds \n" +
            "on ds.specialty_id = s.id where ds.doctor_id = ?1",nativeQuery = true)
    List<Specialty> findAllSpecialtyByDoctorId(int id);


}
