package com.example.telecare.service.impl;

import com.example.telecare.dto.DoctorAchievementDTO;
import com.example.telecare.model.DoctorAchievement;
import com.example.telecare.repository.AchievementRepository;
import com.example.telecare.service.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AchievementServiceImpl implements AchievementService {
    @Autowired
    AchievementRepository achievementRepository;

    @Override
    public List<DoctorAchievementDTO> findAllAchievementByDoctorId(int id) {
        return achievementRepository.findAllAchievementByDoctorId(id);
    }

    @Override
    public DoctorAchievement addDoctorAchievement(DoctorAchievement achievement) {
        return achievementRepository.save(achievement);
    }

}
