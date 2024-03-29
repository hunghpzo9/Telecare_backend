package com.example.telecare.service;

import com.example.telecare.dto.interfaces.DoctorAchievementDTO;
import com.example.telecare.model.DoctorAchievement;

import java.util.List;

public interface AchievementService {
    List<DoctorAchievementDTO> findAllAchievementByDoctorId(int id);

    DoctorAchievement addDoctorAchievement(DoctorAchievement achievement);

    void removeAchievement(int achievementId);
}
