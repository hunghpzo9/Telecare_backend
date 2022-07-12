package com.example.telecare.service;

import com.example.telecare.dto.AdminDTOInf;
import com.example.telecare.dto.DoctorDTO;
import com.example.telecare.model.User;

import java.util.Date;

public interface UserService {

    User registerPatient(User user);
    void registerDoctor(DoctorDTO doctorSignupDTO);
    User registerAdmin(User user);
    void updateStatus(Byte isActive, int id, Date expireDate,String reason);
    void updateStatusForPatient(Byte isActive, int id,String reason);
    User findUserById(int id);
    AdminDTOInf findAdminById(int id);

}
