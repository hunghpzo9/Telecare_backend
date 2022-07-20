package com.example.telecare.dto;

import java.util.Date;

public interface PatientDTOAdminInf {

    Integer getId();

    String getFullName();

    Date getDob();

    String getEmail();

    Byte getGender();

    String getPhone();

    String getImageUrl();

    String getStreetName();

    String getEthnicName();

    String getBloodType();

    Double getHeight();

    Double getWeight();

    String getJob();

    String getJobPlace();

    String getCityName();

    String getDistrictName();

    String getWardName();

    Byte getIsActive();

    String getReason();
}
