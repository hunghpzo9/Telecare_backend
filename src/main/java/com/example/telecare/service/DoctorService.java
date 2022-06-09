package com.example.telecare.service;

import com.example.telecare.dto.DoctorDTOInf;

public interface DoctorService {
    DoctorDTOInf findDoctorById(int uid);
}
