package com.example.telecare.service;

import com.example.telecare.dto.DoctorExperienceDTO;
import com.example.telecare.model.DoctorExperience;

import java.util.List;

public interface ExperienceService {
    List<DoctorExperienceDTO> findAllExperienceByDoctorId(int id);

    DoctorExperience addDoctorExperience(DoctorExperience doctorExperience);

    void removeExperience(int experienceId);


}
