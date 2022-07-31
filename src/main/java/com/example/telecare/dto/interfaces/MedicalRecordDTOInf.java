package com.example.telecare.dto.interfaces;

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


