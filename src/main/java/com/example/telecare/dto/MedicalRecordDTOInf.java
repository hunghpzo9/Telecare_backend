package com.example.telecare.dto;

import java.util.Date;

public interface MedicalRecordDTOInf {
    Integer getId();
    String getMedicalRecordName();
    String getDoctorName();
    Date getCreatedAt();
    String getReason();
    String getMainDisease();
    String getUrl();
}


