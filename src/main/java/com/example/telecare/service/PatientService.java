package com.example.telecare.service;

import com.example.telecare.dto.PatientDTO;
import com.example.telecare.model.Patient;

public interface PatientService {
    PatientDTO findPatientById(int uid);

    void updatePatient(Patient patient,int id);
}
