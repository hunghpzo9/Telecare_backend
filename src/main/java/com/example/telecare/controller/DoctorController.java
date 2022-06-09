package com.example.telecare.controller;

import com.example.telecare.dto.DoctorDTOInf;
import com.example.telecare.service.impl.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 60 * 60 * 24 * 30)
@RestController
@RequestMapping("/api/v1/doctor")
public class DoctorController {
    @Autowired
    DoctorServiceImpl doctorService;

    @GetMapping(value = "/{id}")
    public DoctorDTOInf findPatient(@PathVariable int id) {
        return doctorService.findDoctorById(id);
    }
}
