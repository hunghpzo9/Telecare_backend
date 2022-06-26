package com.example.telecare.service;

import com.example.telecare.dto.AppointmentDTOInf;
import com.example.telecare.model.Appointment;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

public interface AppointmentService {
    List<AppointmentDTOInf> findAppointmentByPatient(int id, List<Integer> statusId);

    AppointmentDTOInf findAppointmentById(int id);

    Appointment createNewAppointment(Appointment appointment, String description, String time);

    List<Integer> listScheduleFindByDoctorAndTime(int doctorId,int patientId, String time);
}
