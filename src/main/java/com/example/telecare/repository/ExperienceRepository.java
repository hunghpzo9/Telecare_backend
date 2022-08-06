package com.example.telecare.repository;

import com.example.telecare.dto.interfaces.DoctorExperienceDTO;
import com.example.telecare.model.DoctorExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceRepository extends JpaRepository <DoctorExperience,Integer>{
    @Query(value = "SELECT de.id,de.experience,de.doctor_id FROM telecare.doctor_experience de left outer join telecare.doctor d \n" +
            "            on d.doctor_id = de.doctor_id where d.doctor_id = ?1",nativeQuery = true)
    List<DoctorExperienceDTO> findAllExperienceByDoctorId(int id);
}
