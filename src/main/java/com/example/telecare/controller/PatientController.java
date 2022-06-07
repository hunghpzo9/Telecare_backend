package com.example.telecare.controller;

import com.example.telecare.dto.PatientDTO;
import com.example.telecare.model.Address;
import com.example.telecare.model.Patient;
import com.example.telecare.repository.PatientRepository;
import com.example.telecare.service.UserService;
import com.example.telecare.service.impl.AddressServiceImpl;
import com.example.telecare.service.impl.PatientServiceImpl;
import com.example.telecare.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(maxAge = 60 * 60 * 24 * 30)
@RestController
@RequestMapping("/api/v1/patient")
public class PatientController {
    @Autowired
    PatientServiceImpl patientService;
    @Autowired
    AddressServiceImpl addressService;
    @Autowired
    UserServiceImpl userService;


    @GetMapping(value = "/{id}")
    public PatientDTO findPatient(@PathVariable int id) {
      return  patientService.findPatientById(id);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable("id") int id,@RequestBody Patient patientDetail) {
        patientService.updatePatient(patientDetail,id);
        return ResponseEntity.ok(patientDetail);
    }
}
