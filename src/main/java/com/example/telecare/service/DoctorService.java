package com.example.telecare.service;

import com.example.telecare.dto.DoctorDTOInf;

import java.util.List;

public interface DoctorService {
    DoctorDTOInf findDoctorById(int uid);

    List<DoctorDTOInf> listAllDoctor(String search, int page);

    List<DoctorDTOInf> listAllDoctorBySpecialty(String search,int specialtyId, int page);
}
