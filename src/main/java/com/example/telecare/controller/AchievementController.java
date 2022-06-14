package com.example.telecare.controller;

import com.example.telecare.model.DoctorAchievement;
import com.example.telecare.repository.AchievementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 60 * 60 * 24 * 30)
@RestController
@RequestMapping("/api/v1/achievement")

public class AchievementController {

    @Autowired
    AchievementRepository achievementRepository;

    @PutMapping(value = "/addAchievement")
    public DoctorAchievement addAchievement(@RequestBody DoctorAchievement doctorAchievement) {
        DoctorAchievement achievement = achievementRepository.save(doctorAchievement);
        return achievement;
    }

}
