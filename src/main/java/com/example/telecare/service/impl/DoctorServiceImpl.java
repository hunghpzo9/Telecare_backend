package com.example.telecare.service.impl;

import com.example.telecare.dto.DoctorAchievementDTO;
import com.example.telecare.dto.DoctorDTOInf;
import com.example.telecare.dto.DoctorExperienceDTO;

import com.example.telecare.dto.DoctorUpdateDTO;
import com.example.telecare.exception.BadRequestException;
import com.example.telecare.exception.NotFoundException;
import com.example.telecare.exception.ResourceNotFoundException;
import com.example.telecare.model.Doctor;

import com.example.telecare.exception.BadRequestException;
import com.example.telecare.exception.NotFoundException;

import com.example.telecare.model.Specialty;
import com.example.telecare.model.User;
import com.example.telecare.repository.DoctorRepository;
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

    @Override
    public DoctorDTOInf findDoctorById(int uid) {
        DoctorDTOInf doctorDTOInf = doctorRepository.findDoctorById(uid);
        if(doctorDTOInf == null) {
            throw new NotFoundException("Không tìm thấy bác sĩ");
        }
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

        System.out.println(user.getEmail());

        User duplicateUserByEmail = userRepository.findUserByEmail(user.getEmail());
        if (duplicateUserByEmail != null && duplicateUserByEmail.getId() != user.getId()) {
            throw new BadRequestException("Email đã tồn tại");
        }

        doctor.setPosition(doctorDetail.getPosition());
        doctor.setJobPlace(doctorDetail.getJobPlace());

        userRepository.save(user);
        doctorRepository.save(doctor);
    }


}
