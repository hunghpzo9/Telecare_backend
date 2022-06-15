package com.example.telecare.service.impl;

import com.example.telecare.model.DoctorSpecialty;
import com.example.telecare.repository.DoctorSpecialtyRepository;
import com.example.telecare.service.DoctorSpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorSpecialtyServiceImpl implements DoctorSpecialtyService {

    @Autowired
    DoctorSpecialtyRepository doctorSpecialtyRepository;

    @Override
    public DoctorSpecialty addDoctorSpecialty(DoctorSpecialty doctorSpecialty) {
        return doctorSpecialtyRepository.save(doctorSpecialty);
    }
}
