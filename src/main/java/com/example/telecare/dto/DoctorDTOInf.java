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
    String getSignature();
    Byte getIsActive();
    String getCertificate();
    String getIdentificationFront();
    String getIdentificationBack();
    Date getExpireDateCertificate();
    Integer getAppointmentDoneCount();
    Integer getPatientCount();
    Double getRating();
    List<Specialty> getListSpecialty();
    List<DoctorAchievementDTO> getListAchievement();
    List<DoctorExperienceDTO> getListExperience();
}
