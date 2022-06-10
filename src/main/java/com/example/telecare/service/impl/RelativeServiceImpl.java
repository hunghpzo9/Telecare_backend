package com.example.telecare.service.impl;

import com.example.telecare.exception.ResourceNotFoundException;
import com.example.telecare.model.Patient;
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
        return relativeRepository.findRelativesByPatientId(patientId);
    }

    @Override
    public Relative addRelative(Relative relative) {
        return relativeRepository.save(relative);
    }

    @Override
    public Relative findRelativeById(int id) {
        return relativeRepository.findRelativesByID(id);
    }

    @Override
    public void updateRelativeById(Relative relativeDetail, int id) {
        Relative relative = relativeRepository.findRelativesByID(id);

        relative.setFullName(relativeDetail.getFullName());
        relative.setDateOfBirth(relativeDetail.getDateOfBirth());
        relative.setGender(relativeDetail.getGender());
        relative.setRelationship(relativeDetail.getRelationship());
        relative.setPhone(relativeDetail.getPhone());
        relative.setEmail(relativeDetail.getEmail());
        relative.setImageUrl(relativeDetail.getImageUrl());

        relativeRepository.save(relative);
    }


}
