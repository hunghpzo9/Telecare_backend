package com.example.telecare.service;

import com.example.telecare.dto.PatientDTO;
import com.example.telecare.dto.interfaces.PatientDTOInf;
import com.example.telecare.dto.interfaces.PatientDTOAdminInf;

import java.util.List;

public interface PatientService {
    PatientDTOInf findPatientById(int uid);

    void updatePatient(PatientDTO patient, int id);

    PatientDTOAdminInf findPatientByIdForAdmin(int uid);

    List<PatientDTOAdminInf> getAllPatient(int index, String search);

    int getNumberOfPatient(String search);
}
