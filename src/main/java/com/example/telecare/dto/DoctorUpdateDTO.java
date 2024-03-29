package com.example.telecare.dto;

import com.example.telecare.model.DoctorAchievement;
import com.example.telecare.model.DoctorExperience;
import com.example.telecare.model.DoctorSpecialty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorUpdateDTO {
    String fullName;
    Date dob;
    Byte gender;
    String email;
    String imageUrl;
    String signature;
    String certificate;
    String position;
    String jobPlace;

    List<DoctorExperience> updateDoctorExperiences;
    List<DoctorExperience> deleteDoctorExperiences;

    List<DoctorAchievement> updateDoctorAchievements;
    List<DoctorAchievement> deleteDoctorAchievements;

    List<Integer> updateDoctorSpecialtyId;
    List<Integer> deleteDoctorSpecialtyId;
}
