package com.example.telecare.dto;

import lombok.*;

import java.util.Date;


public interface PatientDTO {
    Integer getId();

    String getFullName();

    Date getDob();

    String getEmail();

    Byte getGender();

    String getPhone();

    String getImageUrl();

    String getStreetName();

    String getEthnicId();

    String getBloodType();

    Double getHeight();

    Double getWeight();

    String getJob();

    String getJobPlace();

    Integer getCityId();

    Integer getDistrictId();

    Integer getWardId();
}
