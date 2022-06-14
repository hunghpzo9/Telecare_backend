package com.example.telecare.dto;

import com.example.telecare.model.Specialty;

import java.util.Date;
import java.util.List;


public interface DoctorDTOInf {
    Integer getId();
    String getPhone();
    Date getDob();
    Byte getGender();
    String getImageUrl();
    String getFullName();
    String getEmail();
    String getPosition();
    String getJobPlace();
    String getSpecialty();
    Integer getAppointmentDoneCount();
    Integer getPatientCount();
    List<Specialty> getListSpecialty();
    List<DoctorAchievementDTO> getListAchievement();
    List<DoctorExperienceDTO> getListExperience();
}
