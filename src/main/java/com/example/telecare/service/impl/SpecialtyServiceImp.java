package com.example.telecare.service.impl;

import com.example.telecare.model.Doctor;
import com.example.telecare.model.Specialty;
import com.example.telecare.repository.DoctorRepository;
import com.example.telecare.repository.SpecialtyRepository;
import com.example.telecare.service.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialtyServiceImp implements SpecialtyService {

    @Autowired
    SpecialtyRepository specialtyRepository;
    @Autowired
    DoctorRepository doctorRepository;

    @Override
    public List<Specialty> findAllSpecialty() {
        return specialtyRepository.findAll();
    }

    @Override
    public List<Specialty> findAllSpecialtyByDoctorId(int doctorId) {
        return specialtyRepository.findAllSpecialtyByDoctorId(doctorId);
    }

    @Override
    public void removeSpecialty(int doctorId, int specialtyId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();
        Specialty specialty = specialtyRepository.findById(specialtyId).orElseThrow();
        int numberOfSpecialty = doctorRepository.countSpecialtyOfDoctor(doctorId);
        if (numberOfSpecialty > 1) {
            doctor.getSpecialties().remove(specialty);
            doctorRepository.save(doctor);
        } else {
            System.out.println("Phải có ít nhất một chuyên khoa.");
        }
    }
}
