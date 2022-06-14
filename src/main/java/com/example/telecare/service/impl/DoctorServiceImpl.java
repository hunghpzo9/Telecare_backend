package com.example.telecare.service.impl;

import com.example.telecare.dto.DoctorAchievementDTO;
import com.example.telecare.dto.DoctorDTOInf;
import com.example.telecare.dto.DoctorExperienceDTO;
import com.example.telecare.model.Specialty;
import com.example.telecare.repository.DoctorRepository;
import com.example.telecare.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    SpecialtyServiceImp specialtyServiceImp;
    @Autowired
    AchievementServiceImpl achievementService;
    @Autowired
    ExperienceServiceImpl experienceService;
    @Override
    public DoctorDTOInf findDoctorById(int uid) {
        DoctorDTOInf doctorDTOInf = doctorRepository.findDoctorById(uid);
        DoctorDTOInf returnDtoInf = new DoctorDTOInf() {
            @Override
            public Integer getId() {
                return doctorDTOInf.getId();
            }

            @Override
            public String getPhone() {
                return doctorDTOInf.getPhone();
            }

            @Override
            public Date getDob() {
                return doctorDTOInf.getDob();
            }

            @Override
            public Byte getGender() {
                return doctorDTOInf.getGender();
            }

            @Override
            public String getImageUrl() {
                return doctorDTOInf.getImageUrl();
            }

            @Override
            public String getFullName() {
                return  doctorDTOInf.getFullName();
            }

            @Override
            public String getEmail() {
                return doctorDTOInf.getEmail();
            }

            @Override
            public String getPosition() {
                return doctorDTOInf.getPosition();
            }

            @Override
            public String getJobPlace() {
                return doctorDTOInf.getJobPlace();
            }

            @Override
            public String getSpecialty() {
                return doctorDTOInf.getSpecialty();
            }

            @Override
            public Integer getAppointmentDoneCount() {
                return doctorRepository.getNumberDoneAppointment(uid);
            }

            @Override
            public Integer getPatientCount() {
                return doctorRepository.getNumberPatient(uid);
            }

            @Override
            public List<Specialty> getListSpecialty() {
                List<Specialty> specialties = new ArrayList<>();
                for(Specialty s : specialtyServiceImp.findAllSpecialtyByDoctorId(uid)){
                    specialties.add(s);
                }
                return specialties;
            }

            @Override
            public List<DoctorAchievementDTO> getListAchievement() {
                List<DoctorAchievementDTO> doctorAchievements = new ArrayList<>();
                for(DoctorAchievementDTO d : achievementService.findAllAchievementByDoctorId(uid)){
                    doctorAchievements.add(d);
                }
                return doctorAchievements;
            }

            @Override
            public List<DoctorExperienceDTO> getListExperience() {
                List<DoctorExperienceDTO> doctorExperiences = new ArrayList<>();
                for(DoctorExperienceDTO d : experienceService.findAllExperienceByDoctorId(uid)){
                    doctorExperiences.add(d);
                }
                return doctorExperiences;
            }
        };
        return returnDtoInf;
    }

    @Override
    public List<DoctorDTOInf> listAllDoctor(String search,int page) {
        List<DoctorDTOInf> doctorPage = doctorRepository.listAllDoctor(search,page);

        return doctorPage;
    }

    @Override
    public List<DoctorDTOInf> listAllDoctorBySpecialty(String search, List<Integer> specialtyId, int page) {
        List<DoctorDTOInf> doctorPage = doctorRepository.listAllDoctorBySpecialty(search,specialtyId,page);
        return doctorPage;
    }
}
