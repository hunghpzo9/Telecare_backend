package com.example.telecare.service;

import com.example.telecare.dto.AppointmentDTOInf;

import java.util.List;

public interface AppointmentService {
    List<AppointmentDTOInf> findAppointmentByPatient(int id, List<Integer> statusId);

    AppointmentDTOInf findAppointmentById(int id);

    List<Integer> listScheduleFindByDoctorAndTime(int doctorId,int patientId, String time);
}
