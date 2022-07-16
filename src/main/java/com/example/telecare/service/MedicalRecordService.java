package com.example.telecare.service;

import com.example.telecare.dto.MedicalRecordDTOInf;
import com.example.telecare.dto.MedicalRecordDetailDTO;
import com.example.telecare.model.MedicalRecord;

import java.util.List;

public interface MedicalRecordService {
    List<MedicalRecordDTOInf> getAllMedicalRecordByPatientId(int id, int page);

    MedicalRecordDetailDTO getMedicalRecordDetailByAppointmentId(int id);

    MedicalRecord addMedicalRecord(MedicalRecord medicalRecord);

    void updateMedicalRecord(MedicalRecord medicalRecord, int id);
}
