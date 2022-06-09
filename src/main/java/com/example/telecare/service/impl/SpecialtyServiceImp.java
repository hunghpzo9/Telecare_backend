package com.example.telecare.service.impl;

import com.example.telecare.model.Specialty;
import com.example.telecare.repository.SpecialtyRepository;
import com.example.telecare.service.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SpecialtyServiceImp implements SpecialtyService {

    @Autowired
    SpecialtyRepository specialtyRepository;

    @Override
    public List<Specialty> findAllSpecialty() {
        return specialtyRepository.findAll();
    }
}
