package com.example.telecare.service.impl;

import com.example.telecare.dto.interfaces.MedicalRecordDTOInf;
import com.example.telecare.dto.interfaces.MedicalRecordDetailDTO;
import com.example.telecare.model.MedicalRecord;
import com.example.telecare.repository.MedicalRecordRepository;
import com.example.telecare.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    @Override
    public List<MedicalRecordDTOInf> getAllMedicalRecordByPatientId(int id, int page) {
        return medicalRecordRepository.getMedicalRecordByPatientId(id, page);

    }

    @Override
    public MedicalRecordDetailDTO getMedicalRecordDetailByAppointmentId(int id) {
        return medicalRecordRepository.getMedicalRecordDetailsByAppointmentId(id);
    }

    @Override
    public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord, int yearCode) {
        MedicalRecord checkDuplicateTrace = medicalRecordRepository.checkDuplicateTrace(medicalRecord.getTrace());

        do {
            medicalRecord.setTrace(generateMedicalRecordNumber(yearCode));
        } while (checkDuplicateTrace != null);

        return medicalRecordRepository.save(medicalRecord);
    }

    @Override
    public void updateMedicalRecord(MedicalRecord medicalRecordDetails, int id) {
        MedicalRecord medicalRecord = medicalRecordRepository.findMedicalRecordByAppointmentId(id);

        medicalRecord.setUrl(medicalRecordDetails.getUrl());
        medicalRecord.setMedicalRecordName(medicalRecordDetails.getMedicalRecordName());
        medicalRecord.setType(medicalRecordDetails.getType());
        medicalRecord.setExternal(medicalRecordDetails.getExternal());
        medicalRecord.setGuardianDetail(medicalRecordDetails.getGuardianDetail());
        medicalRecord.setGuardianPhone(medicalRecordDetails.getGuardianPhone());
        medicalRecord.setReason(medicalRecordDetails.getReason());
        medicalRecord.setMedicalProcess(medicalRecordDetails.getMedicalProcess());
        medicalRecord.setSelfHistory(medicalRecordDetails.getSelfHistory());
        medicalRecord.setFamilyHistory(medicalRecordDetails.getFamilyHistory());
        medicalRecord.setBodyExamination(medicalRecordDetails.getBodyExamination());
        medicalRecord.setOrgansExamination(medicalRecordDetails.getOrgansExamination());
        medicalRecord.setSummary(medicalRecordDetails.getSummary());
        medicalRecord.setMainDisease(medicalRecordDetails.getMainDisease());
        medicalRecord.setIncludeDisease(medicalRecordDetails.getIncludeDisease());
        medicalRecord.setFirstAmount(medicalRecordDetails.getFirstAmount());
        medicalRecord.setSecondAmount(medicalRecordDetails.getSecondAmount());

        medicalRecordRepository.save(medicalRecord);
    }

    protected String generateMedicalRecordNumber(int yearCode) {
        String root = "1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 6) { // length of the random string.
            int index = (int) (rnd.nextFloat() * root.length());
            salt.append(root.charAt(index));
        }
        String saltStr = salt.toString();
        return "101TLC" + yearCode + saltStr;
    }


}
