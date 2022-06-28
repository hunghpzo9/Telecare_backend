package com.example.telecare.controller;

import com.example.telecare.dto.AppointmentDTOInf;
import com.example.telecare.model.Appointment;
import com.example.telecare.model.CancelAppointment;
import com.example.telecare.service.impl.AppointmentServiceImpl;
import com.example.telecare.service.impl.DoctorServiceImpl;
import com.example.telecare.service.impl.EthnicServiceImpl;
import com.example.telecare.service.impl.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
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

    @PostMapping(value = "/book")
    public ResponseEntity<?> bookAppointment(@RequestBody Appointment appointment
                                            ,@RequestParam("description") String description
                                            ,@RequestParam("time") String time) {
        Appointment newAppointment = appointmentService.createNewAppointment(appointment, description, time);
        return ResponseEntity.ok(newAppointment);
    }

    @PostMapping(value = "/cancel")
    public ResponseEntity<?> cancelAppointment(@RequestBody CancelAppointment cancelAppointment) {
        appointmentService.cancelAppointment(cancelAppointment);
        return ResponseEntity.ok(cancelAppointment);
    }

    @GetMapping(value = "")
    public List<Integer> listScheduleFindByDoctorAndTime(@RequestParam("doctorId") int doctorId,
                                                         @RequestParam("patientId") int patientId,
                                                         @RequestParam("time") String time) {
        return appointmentService.listScheduleFindByDoctorAndTime(doctorId, patientId, time);
    }
}
