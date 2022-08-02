package com.example.telecare.dto.interfaces;

import java.util.Date;

public interface AppointmentDTOInfForAdmin {
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
