package com.example.telecare.controller;

import com.example.telecare.dto.ResponseOkMessage;
import com.example.telecare.model.DoctorAchievement;
import com.example.telecare.service.impl.AchievementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin(maxAge = 60 * 60 * 24 * 30)
@RestController
@RequestMapping("/api/v1/achievement")

public class AchievementController {

    @Autowired
    AchievementServiceImpl achievementService;

    @PutMapping(value = "/addAchievement")
    public DoctorAchievement addAchievement(@RequestBody DoctorAchievement doctorAchievement) {
        DoctorAchievement achievement = achievementService.addDoctorAchievement(doctorAchievement);
        return achievement;
    }

    @DeleteMapping(value = "/removeAchievement")
    public ResponseEntity<?> removeDoctorAchievement(@RequestParam int achievementId) {
        achievementService.removeAchievement(achievementId);
        return ResponseEntity.ok(new ResponseOkMessage("Remove successfully",new Date()));
    }

}
