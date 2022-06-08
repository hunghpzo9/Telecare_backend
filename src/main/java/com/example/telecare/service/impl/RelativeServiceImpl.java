package com.example.telecare.service.impl;

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

}
