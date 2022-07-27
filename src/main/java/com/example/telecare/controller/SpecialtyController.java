package com.example.telecare.controller;

import com.example.telecare.dto.ResponseOkMessage;
import com.example.telecare.model.Specialty;
import com.example.telecare.service.impl.SpecialtyServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(maxAge = 60 * 60 * 24 * 30)
@RestController
@RequestMapping("/api/v1/specialty")
public class SpecialtyController {
    @Autowired
    SpecialtyServiceImp specialtyServiceImp;


    @Cacheable(value = "allSpecialty")
    @GetMapping(value = "")
    public List<Specialty> getAllSpecialty() {
        return specialtyServiceImp.findAllSpecialty();
    }

    @DeleteMapping(value = "/removeSpecialty")
    public ResponseEntity<?> removeSpecialty(@RequestParam int doctorId, @RequestParam int specialtyId) {
        specialtyServiceImp.removeSpecialty(doctorId, specialtyId);
        return ResponseEntity.ok(new ResponseOkMessage("Remove successfully", new Date()));
    }

}
