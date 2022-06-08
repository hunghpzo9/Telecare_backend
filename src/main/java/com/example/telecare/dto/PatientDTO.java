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

    Integer getEthnicId();

    String getBloodType();

    Double getHeight();

    Double getWeight();

    String getJob();

    String getJobPlace();

    String getCityId();

    String getDistrictId();

    String getWardId();
}
