package com.example.telecare.service;

import com.example.telecare.dto.interfaces.DoctorDTOInf;
import com.example.telecare.dto.DoctorUpdateDTO;

import java.util.List;

public interface DoctorService {
    DoctorDTOInf findDoctorById(int uid);

    List<DoctorDTOInf> listAllDoctor(String search, int page);

    List<DoctorDTOInf> listAllDoctorBySpecialty(String search, List<Integer> specialtyId, int page);

    void updateDoctor(DoctorUpdateDTO doctorDetail, int id);

    void addDoctorSpecialty(int doctor, int specialtyId);

    List<DoctorDTOInf> getAllDoctor(int index,String search);

    List<DoctorDTOInf> listAllFavoriteDoctorById(String search, int page,int patientId);

    Boolean isFavoriteDoctor(int patientId, int doctorId);

    void removeFavoriteDoctor(int patientId, int doctorId);

    void addFavoriteDoctor(int patientId, int doctorId);

    int getNumberOfDoctor(String search);

}
