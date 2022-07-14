package com.example.telecare.controller;

import com.example.telecare.dto.MedicalRecordDTOInf;
import com.example.telecare.service.impl.MedicalRecordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 60 * 60 * 24 * 30)
@RestController
@RequestMapping("/api/v1/medicalRecord")
public class MedicalRecordController {
    @Autowired
    MedicalRecordServiceImpl medicalRecordService;

    @GetMapping(value = "/getAll")
    List<MedicalRecordDTOInf> medicalRecordDTOInfList (@RequestParam int patientId, @RequestParam int page){
        return medicalRecordService.getAllMedicalRecordByPatientId(patientId, page);
    }
}
