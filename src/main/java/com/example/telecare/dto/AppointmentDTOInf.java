package com.example.telecare.dto;

import java.util.Date;

public interface AppointmentDTOInf {
    Integer getId();
    Integer getDoctorId();
    Integer getPatientId();
    String getDescription();
    String getSchedule();
    Date getTime();
    String getStatus();
    Integer getStatusId();

    String getPatientName();
    String getPatientImageUrl();
    Byte getPatientGender();
    String getPatientPhone();
    Date getPatientDob();
    String getPatientEthnic();
    String getPatientEmail();
    String getPatientJobPlace();

    String getDoctorName();
    String getDoctorImageUrl();
    Byte getDoctorGender();
    String getDoctorSpecialty();
    String getDoctorPhone();
    String getDoctorEmail();
    String getDoctorJobPlace();
}
