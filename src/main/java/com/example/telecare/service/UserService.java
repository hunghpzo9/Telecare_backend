package com.example.telecare.service;

import com.example.telecare.dto.DoctorDTO;
import com.example.telecare.model.User;

public interface UserService {

    User registerPatient(User user);
    void registerDoctor(DoctorDTO doctorSignupDTO);
    void updateUser(User user);
}
