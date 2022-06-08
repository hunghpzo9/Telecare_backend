package com.example.telecare.service.impl;

import com.example.telecare.dto.PatientDTO;
import com.example.telecare.exception.ResourceNotFoundException;
import com.example.telecare.model.Patient;
import com.example.telecare.repository.PatientRepository;
import com.example.telecare.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    PatientRepository patientRepository;

    @Override
    public PatientDTO findPatientById(int uid) {
        return patientRepository.findPatientById(uid);
    }

    @Override
    public void updatePatient(Patient patientDetail, int id) {
        Patient patient = patientRepository.findById(id) .orElseThrow(()
                -> new ResourceNotFoundException("Not found patient"));

        patient.setBloodType(patientDetail.getBloodType());
        patient.setHeight(patientDetail.getHeight());
        patient.setWeight(patientDetail.getWeight());
        patient.setEthnicId(patientDetail.getEthnicId());
        patient.setJob(patientDetail.getJob());
        patient.setJobPlace(patientDetail.getJobPlace());

        patientRepository.save(patient);
    }
}
