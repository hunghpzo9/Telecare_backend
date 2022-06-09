package com.example.telecare.service.impl;

import com.example.telecare.dto.DoctorDTOInf;
import com.example.telecare.repository.DoctorRepository;
import com.example.telecare.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    DoctorRepository doctorRepository;
    @Override
    public DoctorDTOInf findDoctorById(int uid) {
        return doctorRepository.findDoctorById(uid);
    }
}
