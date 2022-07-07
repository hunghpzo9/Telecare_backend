package com.example.telecare.service;

import com.example.telecare.dto.MedicalRecordDTOInf;

import java.util.List;

public interface MedicalRecord {
    List<MedicalRecordDTOInf> getAllMedicalRecordByPatientId(int id, int page);
}
