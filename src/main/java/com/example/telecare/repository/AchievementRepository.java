package com.example.telecare.repository;

import com.example.telecare.dto.interfaces.DoctorAchievementDTO;
import com.example.telecare.model.DoctorAchievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AchievementRepository extends JpaRepository<DoctorAchievement,Integer> {
    @Query(value = "SELECT da.id,da.achievement FROM telecare.doctor_achievement da left outer join telecare.doctor ds \n" +
            "            on da.doctor_id = ds.doctor_id where ds.doctor_id = ?1",nativeQuery = true)
    List<DoctorAchievementDTO> findAllAchievementByDoctorId(int id);
}
