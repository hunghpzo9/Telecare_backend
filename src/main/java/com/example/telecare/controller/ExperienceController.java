package com.example.telecare.controller;

import com.example.telecare.model.DoctorExperience;
import com.example.telecare.repository.ExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 60 * 60 * 24 * 30)
@RestController
@RequestMapping("/api/v1/experience")

public class ExperienceController {
    @Autowired
    ExperienceRepository experienceRepository;

    @PutMapping(value = "/addExp")
    public DoctorExperience addDoctorExperience(@RequestBody DoctorExperience doctorExperience){
        DoctorExperience experience = experienceRepository.save(doctorExperience);
        return experience;
    }
}
