package com.example.telecare.service;

import com.example.telecare.dto.MedicalRecordDTOInf;
import com.example.telecare.model.MedicalRecord;

import java.util.List;

public interface MedicalRecordServices {
    List<MedicalRecordDTOInf> getAllMedicalRecordByPatientId(int id, int page);

    MedicalRecord addMedicalRecord(MedicalRecord medicalRecord);
}
