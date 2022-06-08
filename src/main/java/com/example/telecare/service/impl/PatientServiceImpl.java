package com.example.telecare.service.impl;

import com.example.telecare.dto.PatientDTO;
import com.example.telecare.exception.ResourceNotFoundException;
import com.example.telecare.model.Address;
import com.example.telecare.model.Patient;
import com.example.telecare.model.User;
import com.example.telecare.repository.AddressRepository;
import com.example.telecare.repository.PatientRepository;
import com.example.telecare.repository.UserRepository;
import com.example.telecare.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AddressRepository addressRepository;
    @Override
    public PatientDTO findPatientById(int uid) {
        return patientRepository.findPatientById(uid);
    }

    @Override
    public void updatePatient(PatientDTO patientDetail, int id) {
        Patient patient = patientRepository.findById(id) .orElseThrow(()
                -> new ResourceNotFoundException("Not found patient"));

        User user = userRepository.findById(id) .orElseThrow(()
                -> new ResourceNotFoundException("Not found user"));

        patient.setBloodType(patientDetail.getBloodType());
        patient.setHeight(patientDetail.getHeight());
        patient.setWeight(patientDetail.getWeight());
        patient.setEthnicId(patientDetail.getEthnicId());
        patient.setJob(patientDetail.getJob());
        patient.setJobPlace(patientDetail.getJobPlace());

        userRepository.save(user);
        patientRepository.save(patient);
    }
}
