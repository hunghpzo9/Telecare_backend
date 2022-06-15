package com.example.telecare.service.impl;

import com.example.telecare.dto.DoctorExperienceDTO;
import com.example.telecare.model.DoctorExperience;
import com.example.telecare.repository.ExperienceRepository;
import com.example.telecare.service.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceServiceImpl implements ExperienceService {
    @Autowired
    ExperienceRepository experienceRepository;

    @Override
    public List<DoctorExperienceDTO> findAllExperienceByDoctorId(int id) {
        return experienceRepository.findAllExperienceByDoctorId(id);
    }

    @Override
    public DoctorExperience addDoctorExperience(DoctorExperience doctorExperience) {
        return experienceRepository.save(doctorExperience);
    }
}
