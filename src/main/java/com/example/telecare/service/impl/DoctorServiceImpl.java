package com.example.telecare.service.impl;

import com.example.telecare.dto.*;

import com.example.telecare.dto.interfaces.DoctorAchievementDTO;
import com.example.telecare.dto.interfaces.DoctorDTOInf;
import com.example.telecare.dto.interfaces.DoctorExperienceDTO;
import com.example.telecare.exception.BadRequestException;
import com.example.telecare.exception.NotFoundException;
import com.example.telecare.exception.ResourceNotFoundException;
import com.example.telecare.model.Doctor;


import com.example.telecare.model.Patient;
import com.example.telecare.model.Specialty;
import com.example.telecare.model.User;
import com.example.telecare.repository.DoctorRepository;
import com.example.telecare.repository.PatientRepository;
import com.example.telecare.repository.SpecialtyRepository;
import com.example.telecare.repository.UserRepository;
import com.example.telecare.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    PatientRepository patientRepository;
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
            public Byte getIsActive() {
                return doctorDTOInf.getIsActive();
            }

            @Override
            public String getCertificate() {
                return doctorDTOInf.getCertificate();
            }

            @Override
            public String getIdentificationFront() {
                return doctorDTOInf.getIdentificationFront();
            }

            @Override
            public String getIdentificationBack() {
                return doctorDTOInf.getIdentificationBack();
            }

            @Override
            public Date getExpireDateCertificate() {
                return doctorDTOInf.getExpireDateCertificate();
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

            @Override
            public String getReason() {
                return doctorDTOInf.getReason();
            }
        };
        return returnDtoInf;
    }

    @Override
    public List<DoctorDTOInf> listAllDoctor(String search, int page) {
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = formatter.format(cld.getTime());
        System.out.println(currentDate);

        List<DoctorDTOInf> doctorPage = doctorRepository.listAllDoctor(search, page,currentDate);
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
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = formatter.format(cld.getTime());


        List<DoctorDTOInf> doctorPage = doctorRepository.listAllDoctorBySpecialty(search, specialtyId, page,currentDate);
        List<DoctorDTOInf> returnDoctorPage = new ArrayList<>();
        for (DoctorDTOInf doctorDTOInf : doctorPage) {
            DoctorDTOInf finalDoctorDTO = doctorDTOInf;
            doctorDTOInf = setReturnDoctor(finalDoctorDTO);
            returnDoctorPage.add(doctorDTOInf);
        }
        return returnDoctorPage;
    }
    @Override
    public List<DoctorDTOInf> listAllFavoriteDoctorById(String search, int page, int patientId) {
        List<DoctorDTOInf> doctorPage = doctorRepository.listAllFavoriteDoctorById(search,page,patientId);
        List<DoctorDTOInf> returnDoctorPage = new ArrayList<>();
        for (DoctorDTOInf doctorDTOInf : doctorPage) {
            DoctorDTOInf finalDoctorDTO = doctorDTOInf;
            doctorDTOInf = setReturnDoctor(finalDoctorDTO);
            returnDoctorPage.add(doctorDTOInf);
        }
        return returnDoctorPage;
    }

    @Override
    public Boolean isFavoriteDoctor(int patientId, int doctorId) {
        if(doctorRepository.countFavoriteDoctor(patientId,doctorId) > 0){
            return true;
        }
        return false;
    }

    @Override
    public void removeFavoriteDoctor(int patientId, int doctorId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow();
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();
        patient.getFavoriteDoctor().remove(doctor);
        patientRepository.save(patient);
    }

    @Override
    public void addFavoriteDoctor(int patientId, int doctorId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow();
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();
        Set<Doctor> favoriteDoctor = patient.getFavoriteDoctor();
        favoriteDoctor.add(doctor);
        patient.setFavoriteDoctor(favoriteDoctor);
        patientRepository.save(patient);
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
        doctor.setSignature(doctorDetail.getSignature());

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

    @Override
    public List<DoctorDTOInf> getAllDoctor(int index,String search) {
        List<DoctorDTOInf> doctorPage = doctorRepository.getAllDoctor(index,search);
        List<DoctorDTOInf> returnDoctorPage = new ArrayList<>();
        for (DoctorDTOInf doctorDTOInf : doctorPage) {
            DoctorDTOInf finalDoctorDTO = doctorDTOInf;
            doctorDTOInf = setReturnDoctor(finalDoctorDTO);
            returnDoctorPage.add(doctorDTOInf);
        }
        return returnDoctorPage;
    }

    @Override
    public int getNumberOfDoctor(String searh) {
        return doctorRepository.getNumberOfDoctor(searh);
    }


}
