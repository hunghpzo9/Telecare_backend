package com.example.telecare.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO {
    Integer id;
    String phone;
    String fullName;
    String email;
    String password;
    Byte isActive;
    String position;
    String jobPlace;
    String certificate;
    String identificationFront;
    String identificationBack;
    String signature;
    Integer specialtyId;
}
