package com.example.telecare.service;

import com.example.telecare.dto.DoctorExperienceDTO;

import java.util.List;

public interface ExperienceService {
    List<DoctorExperienceDTO> findAllExperienceByDoctorId(int id);
}
