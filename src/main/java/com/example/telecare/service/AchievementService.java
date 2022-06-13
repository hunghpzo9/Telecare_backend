package com.example.telecare.service;

import com.example.telecare.dto.DoctorAchievementDTO;

import java.util.List;

public interface AchievementService {
    List<DoctorAchievementDTO> findAllAchievementByDoctorId(int id);
}
