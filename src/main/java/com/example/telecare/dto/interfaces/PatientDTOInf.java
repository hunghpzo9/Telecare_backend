package com.example.telecare.dto.interfaces;
import com.example.telecare.dto.PatientDTO;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;

@JsonDeserialize(as = PatientDTO.class)
public interface PatientDTOInf {
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

    Byte getIsActive();

    String getReason();

    String getInsurance();
}
