package com.example.telecare.controller;
import com.example.telecare.model.Specialty;
import com.example.telecare.service.impl.SpecialtyServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(maxAge = 60 * 60 * 24 * 30)
@RestController
@RequestMapping("/api/v1/specialty")
public class SpecialtyController {
    @Autowired
    SpecialtyServiceImp specialtyServiceImp;

    @Cacheable(value="allSpecialty")
    @GetMapping(value = "")
    public List<Specialty> getAllSpecialty() {
        return specialtyServiceImp.findAllSpecialty();
    }
}
