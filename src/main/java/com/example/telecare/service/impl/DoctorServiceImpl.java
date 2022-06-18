package com.example.telecare.service.impl;

import com.example.telecare.dto.*;

import com.example.telecare.exception.BadRequestException;
import com.example.telecare.exception.NotFoundException;
import com.example.telecare.exception.ResourceNotFoundException;
import com.example.telecare.model.Doctor;


import com.example.telecare.model.Specialty;
import com.example.telecare.model.User;
import com.example.telecare.repository.DoctorRepository;
import com.example.telecare.repository.SpecialtyRepository;
import com.example.telecare.repository.UserRepository;
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
    @Autowired
    UserRepository userRepository;
    @Autowired
    SpecialtyRepository specialtyRepository;

    @Override
    public DoctorDTOInf findDoctorById(int uid) {
        DoctorDTOInf doctorDTOInf = doctorRepository.findDoctorById(uid);
        if (doctorDTOInf == null) {
            throw new NotFoundException("Không tìm thấy bác sĩ");
        }
        return setReturnDoctor(doctorDTOInf);
    }

    private DoctorDTOInf setReturnDoctor(DoctorDTOInf doctorDTOInf) {
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
                return doctorDTOInf.getFullName();
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
            public String getSignature() {
                return doctorDTOInf.getSignature();
            }

            @Override
            public Integer getAppointmentDoneCount() {
                return doctorRepository.getNumberDoneAppointment(doctorDTOInf.getId());
            }

            @Override
            public Integer getPatientCount() {
                return doctorRepository.getNumberPatient(doctorDTOInf.getId());
            }

            @Override
            public Double getRating() {
                return doctorRepository.getAverageRating(doctorDTOInf.getId()) == null
                        ? 0 : doctorRepository.getAverageRating(doctorDTOInf.getId());
            }

            @Override
            public List<Specialty> getListSpecialty() {
                List<Specialty> specialties = new ArrayList<>();
                for (Specialty s : specialtyServiceImp.findAllSpecialtyByDoctorId(doctorDTOInf.getId())) {
                    specialties.add(s);
                }
                return specialties;
            }

            @Override
            public List<DoctorAchievementDTO> getListAchievement() {
                List<DoctorAchievementDTO> doctorAchievements = new ArrayList<>();
                for (DoctorAchievementDTO d : achievementService.findAllAchievementByDoctorId(doctorDTOInf.getId())) {
                    doctorAchievements.add(d);
                }
                return doctorAchievements;
            }

            @Override
            public List<DoctorExperienceDTO> getListExperience() {
                List<DoctorExperienceDTO> doctorExperiences = new ArrayList<>();
                for (DoctorExperienceDTO d : experienceService.findAllExperienceByDoctorId(doctorDTOInf.getId())) {
                    doctorExperiences.add(d);
                }
                return doctorExperiences;
            }
        };
        return returnDtoInf;
    }

    @Override
    public List<DoctorDTOInf> listAllDoctor(String search, int page) {
        List<DoctorDTOInf> doctorPage = doctorRepository.listAllDoctor(search, page);
        List<DoctorDTOInf> returnDoctorPage = new ArrayList<>();
        for (DoctorDTOInf doctorDTOInf : doctorPage) {
            DoctorDTOInf finalDoctorDTO = doctorDTOInf;
            doctorDTOInf = setReturnDoctor(finalDoctorDTO);
            returnDoctorPage.add(doctorDTOInf);
        }
        return returnDoctorPage;
    }

    @Override
    public List<DoctorDTOInf> listAllDoctorBySpecialty(String search, List<Integer> specialtyId, int page) {
        List<DoctorDTOInf> doctorPage = doctorRepository.listAllDoctorBySpecialty(search, specialtyId, page);
        List<DoctorDTOInf> returnDoctorPage = new ArrayList<>();
        for (DoctorDTOInf doctorDTOInf : doctorPage) {
            DoctorDTOInf finalDoctorDTO = doctorDTOInf;
            doctorDTOInf = setReturnDoctor(finalDoctorDTO);
            returnDoctorPage.add(doctorDTOInf);
        }
        return returnDoctorPage;
    }

    @Override
    public void updateDoctor(DoctorUpdateDTO doctorDetail, int id) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Not found doctor"));

        User user = userRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Not found user"));


        user.setFullName(doctorDetail.getFullName());
        user.setDateOfBirth(doctorDetail.getDob());
        user.setGender(doctorDetail.getGender());
        user.setEmail(doctorDetail.getEmail());
        user.setImageUrl(doctorDetail.getImageUrl());


        System.out.println(user.getEmail());

        User duplicateUserByEmail = userRepository.findUserByEmail(user.getEmail());
        if (duplicateUserByEmail != null && duplicateUserByEmail.getId() != user.getId()) {
            throw new BadRequestException("Email đã tồn tại");
        }

        doctor.setPosition(doctorDetail.getPosition());
        doctor.setJobPlace(doctorDetail.getJobPlace());
        doctor.setSignature(doctorDetail.getSignatureUrl());

        userRepository.save(user);
        doctorRepository.save(doctor);
    }

    @Override
    public void addDoctorSpecialty(int doctorId, int specialtyId) {

        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(()
                -> new ResourceNotFoundException("Not found doctor"));

        doctor.addSpecialty(specialtyRepository.findSpecialtyById(specialtyId));

        doctorRepository.save(doctor);
    }


}
