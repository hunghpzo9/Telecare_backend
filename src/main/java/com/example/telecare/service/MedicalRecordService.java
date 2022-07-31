package com.example.telecare.service;

import com.example.telecare.dto.interfaces.MedicalRecordDTOInf;
import com.example.telecare.dto.interfaces.MedicalRecordDetailDTO;
import com.example.telecare.model.MedicalRecord;

import java.util.List;

public interface MedicalRecordService {
    List<MedicalRecordDTOInf> getAllMedicalRecordByPatientId(int id, int page);

    List<MedicalRecordDTOInf> getShareMedicalRecord(int id, int page,boolean isRelative,int relativeId);

    MedicalRecordDetailDTO getMedicalRecordDetailByAppointmentId(int id);

    MedicalRecord addMedicalRecord(MedicalRecord medicalRecord, int yearCode);

    void updateMedicalRecord(MedicalRecord medicalRecord, int id);
}
