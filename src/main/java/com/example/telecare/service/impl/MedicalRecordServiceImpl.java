package com.example.telecare.service.impl;

import com.example.telecare.dto.MedicalRecordDTOInf;
import com.example.telecare.model.MedicalRecord;
import com.example.telecare.repository.MedicalRecordRepository;
import com.example.telecare.service.MedicalRecordServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordServices{

    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    @Override
    public List<MedicalRecordDTOInf> getAllMedicalRecordByPatientId(int id, int page) {
        return medicalRecordRepository.getMedicalRecordByPatientId(id, page);

    }
    @Override
    public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordRepository.save(medicalRecord);
    }
}
