package com.example.telecare.dto;

import java.util.Date;

public interface AppointmentDTOInf2 {
    Integer getPatientId();
    String getPatientName();
    String getPatientPhone();
    String getDoctorName();
    Integer getDoctorId();
    String getPrescriptionTrace();
    String getPrescriptionUrl();
    String getMedicalRecordTrace();
    String getMedicalRecordUrl();
    Date getTime();
}
