package com.example.telecare.dto.interfaces;

public interface ReportDTOInfForAdmin {
    Integer getId();
    String getReportPerson();
    Integer getUserId();


    Integer getAppointmentId();
    String getDescription();
    String getReason();
    String getTime();
    Integer getStatusId();
    String getStatusName();

}
