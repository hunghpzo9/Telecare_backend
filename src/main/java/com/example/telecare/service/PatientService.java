package com.example.telecare.service;

import com.example.telecare.dto.PatientDTO;
import com.example.telecare.dto.PatientDTOInf;
import com.example.telecare.dto.PatientDTOInf2;

import java.util.List;

public interface PatientService {
    PatientDTOInf findPatientById(int uid);

    void updatePatient(PatientDTO patient, int id);

    PatientDTOInf2 findPatientByIdForAdmin(int uid);

    List<PatientDTOInf2> getAllPatient(int index, String search);

    int getNumberOfPatient(String search);
}
