package com.example.telecare.dto.interfaces;

public interface ReportDTOInfForAdmin {
    Integer getId();
    String getPatientName();
    Integer getPatientId();
    String getDoctorName();
    Integer getDoctorId();
    Integer getAppointmentId();
    String getDescription();
    String getReason();
    String getTime();
    Integer getStatusId();
    String getStatusName();

}
