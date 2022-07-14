package com.example.telecare.service.impl;

import com.example.telecare.dto.MedicalRecordDTOInf;
import com.example.telecare.repository.MedicalRecordRepository;
import com.example.telecare.service.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MedicalRecordServiceImpl implements MedicalRecord {

    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    @Override
    public List<MedicalRecordDTOInf> getAllMedicalRecordByPatientId(int id, int page) {
        return medicalRecordRepository.getMedicalRecordByPatientId(id, page);

    }
}
