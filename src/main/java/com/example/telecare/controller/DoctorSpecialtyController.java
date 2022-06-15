package com.example.telecare.controller;

import com.example.telecare.model.DoctorSpecialty;
import com.example.telecare.repository.DoctorSpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 60 * 60 * 24 * 30)
@RestController
@RequestMapping("/api/v1/specialty")

public class DoctorSpecialtyController {
    @Autowired
    DoctorSpecialtyRepository doctorSpecialtyRepository;

    @PutMapping(value = "/addSpecialty")
    public DoctorSpecialty addDoctorSpecialty(@RequestBody DoctorSpecialty doctorSpecialtyDetail) {
        DoctorSpecialty doctorSpecialty = doctorSpecialtyRepository.save(doctorSpecialtyDetail);
        return doctorSpecialty;
    }
}
