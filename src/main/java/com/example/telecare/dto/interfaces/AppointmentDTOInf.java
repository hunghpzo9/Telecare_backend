package com.example.telecare.dto.interfaces;

import java.util.Date;
import java.sql.Time;
public interface AppointmentDTOInf {
    Integer getId();
    Integer getDoctorId();
    Integer getPatientId();
    String getDescription();
    Time getStartAt();
    Time getEndAt();
    String getTime();
    String getStatus();
    Integer getStatusId();
    Integer getRelativeId();
    Integer getAmount();
    Byte getIsShare();

    String getPatientName();
    String getPatientImageUrl();
    Byte getPatientGender();
    String getPatientPhone();
    Date getPatientDob();
    String getPatientEthnic();
    String getPatientEmail();
    String getPatientAddress();

    String getDoctorName();
    String getDoctorImageUrl();
    Byte getDoctorGender();
    String getDoctorSpecialty();
    String getDoctorPhone();
    String getDoctorEmail();
    String getDoctorJobPlace();
}
