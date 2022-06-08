package com.example.telecare.dto;

import com.example.telecare.model.Patient;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import java.util.Date;

@JsonDeserialize(as = Patient.class)
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

    Integer getAddressId();

    String getCityId();

    String getDistrictId();

    String getWardId();
}
