package com.example.telecare.service.impl;

import com.example.telecare.exception.NotFoundException;
import com.example.telecare.model.Relative;
import com.example.telecare.repository.RelativeRepository;
import com.example.telecare.service.RelativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelativeServiceImpl implements RelativeService {

    @Autowired
    RelativeRepository relativeRepository;

    @Override
    public List<Relative> findAllRelativeByPatientId(int patientId) {
        if (patientId < 1) {
            throw new NotFoundException("Relative not found! PatientID is incorrect");
        }
        try {
            return relativeRepository.findRelativesByPatientId(patientId);
        } catch (Exception e) {
            throw new NotFoundException("Relative not found! PatientID does not exist");
        }
    }

    @Override
    public Relative addRelative(Relative relative) {
        if (relative.getEthnicId() == null) {
            throw new NotFoundException("EthnicID is not null");
        }
        if (relative.getPatientId() == null) {
            throw new NotFoundException("PatientID is not null");
        }
        if (relative.getEthnicId() < 1) {
            throw new NotFoundException("Ethnic is not found! EthnicID is incorrect");
        }
        if (relative.getPatientId() < 1) {
            throw new NotFoundException("Patient not found! PatientID is incorrect");
        }
        try {
            return relativeRepository.save(relative);
        } catch (Exception e) {
            throw new NotFoundException("Add fail! Make sure your data is correct");
        }

    }

    @Override
    public Relative findRelativeById(int id) {
        if (id < 1) {
            throw new NotFoundException("Relative not found! RelativeID is incorrect");
        }
        try {
            return relativeRepository.findRelativesByID(id);
        } catch (Exception e) {
            throw new NotFoundException("Relative not found! RelativeID does not exist");
        }
    }


    @Override
    public void updateRelativeById(Relative relativeDetail, int id) {
        if (id < 1) {
            throw new NotFoundException("Relative not found! RelativeID is incorrect");
        }
        try {
            Relative relative = relativeRepository.findRelativesByID(id);

            relative.setFullName(relativeDetail.getFullName());
            relative.setDateOfBirth(relativeDetail.getDateOfBirth());
            relative.setGender(relativeDetail.getGender());
            relative.setRelationship(relativeDetail.getRelationship());
            relative.setPhone(relativeDetail.getPhone());
            relative.setEmail(relativeDetail.getEmail());
            relative.setImageUrl(relativeDetail.getImageUrl());

            relativeRepository.save(relative);

        } catch (Exception e) {
            throw new NotFoundException("Update fail! Make sure your data is correct");
        }

    }


}
