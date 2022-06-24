package com.example.telecare.service;

import com.example.telecare.dto.DoctorDTO;
import com.example.telecare.dto.DoctorDTOInf;
import com.example.telecare.dto.DoctorUpdateDTO;
import com.example.telecare.model.Doctor;
import com.example.telecare.model.DoctorAchievement;

import java.util.List;

public interface DoctorService {
    DoctorDTOInf findDoctorById(int uid);

    List<DoctorDTOInf> listAllDoctor(String search, int page);

    List<DoctorDTOInf> listAllDoctorBySpecialty(String search, List<Integer> specialtyId, int page);

    void updateDoctor(DoctorUpdateDTO doctorDetail, int id);

    void addDoctorSpecialty(int doctor, int specialtyId);

    List<DoctorDTOInf> getAllDoctor(int index);

}
