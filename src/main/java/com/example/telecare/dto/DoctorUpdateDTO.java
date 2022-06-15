package com.example.telecare.dto;

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
    String position;
    String jobPlace;
}
