package com.example.telecare.service.impl;

import com.example.telecare.dto.interfaces.MedicalRecordDTOInf;
import com.example.telecare.dto.interfaces.MedicalRecordDetailDTO;
import com.example.telecare.exception.BadRequestException;
import com.example.telecare.exception.NotFoundException;
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
        if (page < 0) {
            throw new NotFoundException("Medical Record not found! Index can not less than 0");
        }
        if (id < 1) {
            throw new NotFoundException("Medical Record not found! Patient ID is incorrect");
        }
        try {
            return medicalRecordRepository.getMedicalRecordByPatientId(id, page);
        } catch (Exception e) {
            throw new NotFoundException("Medical Record not found! Patient id does not exist");
        }

    }

    @Override
    public List<MedicalRecordDTOInf> getShareMedicalRecord(int id, int page, boolean isRelative, int relativeId) {
        return medicalRecordRepository.getShareMedicalRecord(id, page, isRelative, relativeId);
    }

    @Override
    public MedicalRecordDetailDTO getMedicalRecordDetailByAppointmentId(int id) {
        if (id < 1) {
            throw new NotFoundException("Medical Record not found! Appointment id is incorrect");
        }
        try {
            return medicalRecordRepository.getMedicalRecordDetailsByAppointmentId(id);
        } catch (Exception e) {
            throw new NotFoundException("Medical Record not found! Appointment id does not exist");
        }
    }

    @Override
    public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord, int yearCode) {
        if (yearCode < 0) {
            throw new BadRequestException("Incorrect year code");
        }
        if (medicalRecord.getAppointmentId() < 1) {
            throw new BadRequestException("Add fail! AppointmentID is in correct");
        }
        try {
            MedicalRecord checkDuplicateTrace = medicalRecordRepository.checkDuplicateTrace(medicalRecord.getTrace());
            do {
                medicalRecord.setTrace(generateMedicalRecordNumber(yearCode));
            } while (checkDuplicateTrace != null);

            return medicalRecordRepository.save(medicalRecord);
        } catch (Exception e) {
            throw new NotFoundException("Add fail! Make sure your data is correct");
        }
    }

    @Override
    public void updateMedicalRecord(MedicalRecord medicalRecordDetails, int id) {
        if (id < 1) {
            throw new NotFoundException("Medical Record not found! Appointment id is incorrect");
        }
        try {
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
        } catch (Exception e) {
            throw new NotFoundException("Update fail! Make sure your data is correct");
        }
    }

    @Override
    public List<MedicalRecordDTOInf> getSharedMedicalRecordByAppointment(int id, int page) {
        return medicalRecordRepository.getSharedMedicalRecordByAppointment(id, page);
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
