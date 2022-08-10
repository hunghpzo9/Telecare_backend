package com.example.telecare.dto.interfaces;

import java.util.Date;

public interface PrescriptionDTOInf {
    Integer getId();
    String getPrescriptionDiagnosis();
    String getDoctorName();
    Date getCreatedAt();
    String getUrl();
    Integer getAppointmentId();
}
