package com.example.telecare.controller;

import com.example.telecare.dto.AppointmentDTOInf;
import com.example.telecare.dto.DoctorDTOInf;
import com.example.telecare.dto.PatientDTOInf;
import com.example.telecare.service.impl.AppointmentServiceImpl;
import com.example.telecare.service.impl.DoctorServiceImpl;
import com.example.telecare.service.impl.EthnicServiceImpl;
import com.example.telecare.service.impl.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(maxAge = 60 * 60 * 24 * 30)
@RestController
@RequestMapping("/api/v1/appointment")
public class AppointmentController {
    @Autowired
    AppointmentServiceImpl appointmentService;
    @Autowired
    PatientServiceImpl patientService;
    @Autowired
    DoctorServiceImpl doctorService;

    @Autowired
    EthnicServiceImpl ethnicService;

    @GetMapping(value = "/patientId={id}")
    public List<AppointmentDTOInf> getAppointmentByPatientId(@PathVariable int id, @RequestParam("statusId") List<Integer> statusId) {
        return appointmentService.findAppointmentByPatient(id, statusId);
    }

    @GetMapping(value = "/{id}")
    public AppointmentDTOInf getAppointmentById(@PathVariable int id) {
       return appointmentService.findAppointmentById(id);
    }
}