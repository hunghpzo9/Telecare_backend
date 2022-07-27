package com.example.telecare.controller;

import com.example.telecare.dto.MedicalRecordDTOInf;
import com.example.telecare.dto.MedicalRecordDetailDTO;
import com.example.telecare.model.MedicalRecord;
import com.example.telecare.service.impl.MedicalRecordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 60 * 60 * 24 * 30)
@RestController
@RequestMapping("/api/v1/medicalRecord")
public class MedicalRecordController {
    @Autowired
    MedicalRecordServiceImpl medicalRecordService;

    @GetMapping(value = "/getAll")
    public List<MedicalRecordDTOInf> getAllMedicalRecordByPatientId(@RequestParam int patientId, @RequestParam int page) {
        return medicalRecordService.getAllMedicalRecordByPatientId(patientId, page);
    }

    @GetMapping(value = "/getMedicalRecordDetail")
    public MedicalRecordDetailDTO medicalRecordDTOInfList(@RequestParam int appointmentId) {
        return medicalRecordService.getMedicalRecordDetailByAppointmentId(appointmentId);
    }

    @PostMapping(value = "/addMedicalRecord")
    public MedicalRecord addMedicalRecord(@RequestBody MedicalRecord medicalRecord, @RequestParam int yearCode) {
        MedicalRecord addMedicalRecord = medicalRecordService.addMedicalRecord(medicalRecord, yearCode);
        return addMedicalRecord;
    }

    @PutMapping(value = "/updateMedicalRecord")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@RequestBody MedicalRecord medicalRecord, @RequestParam int appointmentId) {
        medicalRecordService.updateMedicalRecord(medicalRecord, appointmentId);
        return ResponseEntity.ok(medicalRecord);
    }
}
