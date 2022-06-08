package com.example.telecare.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {
    Integer id;

    String fullName;

    Date dob;

    String email;

    Byte gender;

    String phone;

    String imageUrl;

    String streetName;

    Integer ethnicId;

    String bloodType;

    Double height;

    Double weight;

    String job;

    String jobPlace;

    Integer addressId;

    String cityId;

    String districtId;

    String wardId;
}
