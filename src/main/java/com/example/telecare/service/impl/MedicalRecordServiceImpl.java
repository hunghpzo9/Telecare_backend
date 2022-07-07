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

    private MedicalRecordDTOInf setMedicalRecord(MedicalRecordDTOInf medicalRecordDTOInf) {
        MedicalRecordDTOInf returnDtoInf = new MedicalRecordDTOInf() {

            @Override
            public Integer getId() {
                return medicalRecordDTOInf.getId();
            }

            @Override
            public String getMedicalRecordName() {
                return medicalRecordDTOInf.getMedicalRecordName();
            }

            @Override
            public String getDoctorName() {
                return medicalRecordDTOInf.getDoctorName();
            }

            @Override
            public Date getCreatedAt() {
                return medicalRecordDTOInf.getCreatedAt();
            }

            @Override
            public String getReason() {
                return medicalRecordDTOInf.getReason();
            }

            @Override
            public String getMainDisease() {
                return medicalRecordDTOInf.getMainDisease();
            }
        };
        return returnDtoInf;
    }

    @Override
    public List<MedicalRecordDTOInf> getAllMedicalRecordByPatientId(int id, int page) {
        List<MedicalRecordDTOInf> medicalRecordList = medicalRecordRepository.getMedicalRecordByPatientId(id, page);
        List<MedicalRecordDTOInf> returnMedicalRecordList = new ArrayList<>();
        for (MedicalRecordDTOInf medicalRecordDTOInf : medicalRecordList
        ) {
            MedicalRecordDTOInf finalMedicalRecordDTO = medicalRecordDTOInf;
            medicalRecordDTOInf = setMedicalRecord(finalMedicalRecordDTO);
            returnMedicalRecordList.add(medicalRecordDTOInf);
        }
        return returnMedicalRecordList;
    }
}
