package com.example.telecare.controller;

import com.example.telecare.dto.ResponseOkMessage;
import com.example.telecare.model.DoctorExperience;
import com.example.telecare.service.impl.ExperienceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin(maxAge = 60 * 60 * 24 * 30)
@RestController
@RequestMapping("/api/v1/experience")

public class ExperienceController {
    @Autowired
    ExperienceServiceImpl experienceService;

    @PutMapping(value = "/addExp")
    public DoctorExperience addDoctorExperience(@RequestBody DoctorExperience doctorExperience) {
        DoctorExperience experience = experienceService.addDoctorExperience(doctorExperience);
        return experience;
    }

    @DeleteMapping(value = "/removeExp")
    public ResponseEntity<?> removeDoctorExperience(@RequestParam int experienceId) {
        experienceService.removeExperience(experienceId);
        return ResponseEntity.ok(new ResponseOkMessage("Remove successfully",new Date()));
    }
}
