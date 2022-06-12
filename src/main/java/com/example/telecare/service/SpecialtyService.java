package com.example.telecare.service;


import com.example.telecare.model.Specialty;

import java.util.List;

public interface SpecialtyService {
    List<Specialty> findAllSpecialty();

    List<Specialty> findAllSpecialtyByDoctorId(int doctorId);
}
