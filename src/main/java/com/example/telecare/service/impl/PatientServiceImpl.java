package com.example.telecare.service.impl;

import com.example.telecare.dto.PatientDTO;
import com.example.telecare.dto.interfaces.PatientDTOInf;
import com.example.telecare.dto.interfaces.PatientDTOAdminInf;
import com.example.telecare.exception.BadRequestException;
import com.example.telecare.exception.NotFoundException;
import com.example.telecare.model.Address;
import com.example.telecare.model.Patient;
import com.example.telecare.model.User;
import com.example.telecare.repository.AddressRepository;
import com.example.telecare.repository.PatientRepository;
import com.example.telecare.repository.UserRepository;
import com.example.telecare.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AddressRepository addressRepository;

    @Override
    public PatientDTOInf findPatientById(int uid) {
        PatientDTOInf patientDTOIn = patientRepository.findPatientById(uid);
        if (uid < 1) {
            throw new NotFoundException("Patient not found! PatientID is in correct");
        }
        if (patientDTOIn == null) {
            throw new NotFoundException("Patient does not exist");
        }
        return patientDTOIn;
    }

    @Override
    public void updatePatient(PatientDTO patientDetail, int id) {
        Patient patient = patientRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Not found patient"));

        User user = userRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Not found user"));

        Address address = addressRepository.findById(user.getAddress().getId()).orElseThrow(()
                -> new NotFoundException("Not found address"));

        patient.setBloodType(patientDetail.getBloodType());
        patient.setHeight(patientDetail.getHeight());
        patient.setWeight(patientDetail.getWeight());
        patient.setEthnicId(patientDetail.getEthnicId());
        patient.setJob(patientDetail.getJob());
        patient.setJobPlace(patientDetail.getJobPlace());
        patient.setInsurance(patientDetail.getInsurance());

        user.setFullName(patientDetail.getFullName());
        user.setGender(patientDetail.getGender());
        user.setDateOfBirth(patientDetail.getDob());
        user.setEmail(patientDetail.getEmail());
        user.setImageUrl(patientDetail.getImageUrl());

        User duplicateUserByEmail = userRepository.findUserByEmail(user.getEmail());
        if (duplicateUserByEmail != null && duplicateUserByEmail.getId() != user.getId()) {
            throw new BadRequestException("Email đã tồn tại");
        }

        address.setCityId(patientDetail.getCityId());
        address.setDistrictId(patientDetail.getDistrictId());
        address.setWardId(patientDetail.getWardId());
        address.setStreetName(patientDetail.getStreetName());

        userRepository.save(user);
        patientRepository.save(patient);
    }

    @Override
    public PatientDTOAdminInf findPatientByIdForAdmin(int uid) {
        return patientRepository.findPatientByIdForAdmin(uid);
    }

    @Override
    public List<PatientDTOAdminInf> getAllPatient(int index, String search) {
        return patientRepository.getAllPatient(index, search);
    }

    @Override
    public int getNumberOfPatient(String search) {
        return patientRepository.getNumberOfPatient(search);
    }
}
