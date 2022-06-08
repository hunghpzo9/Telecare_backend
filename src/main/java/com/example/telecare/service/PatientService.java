package com.example.telecare.service;

import com.example.telecare.dto.PatientDTO;
import com.example.telecare.dto.PatientDTOInf;

public interface PatientService {
    PatientDTOInf findPatientById(int uid);

    void updatePatient(PatientDTO patient, int id);
}
