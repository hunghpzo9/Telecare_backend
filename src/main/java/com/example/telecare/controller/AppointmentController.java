package com.example.telecare.controller;

import com.example.telecare.dto.*;
import com.example.telecare.model.Appointment;
import com.example.telecare.model.AppointmentDetails;
import com.example.telecare.model.CancelAppointment;
import com.example.telecare.service.impl.AppointmentServiceImpl;
import com.example.telecare.service.impl.DoctorServiceImpl;
import com.example.telecare.service.impl.EthnicServiceImpl;
import com.example.telecare.service.impl.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
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

    @GetMapping(value = "/doctorId={id}")
    public List<AppointmentDTOInf> getAppointmentByDoctor(@PathVariable int id, @RequestParam("statusId") List<Integer> statusId) {
        return appointmentService.findAppointmentByDoctor(id, statusId);
    }

    @GetMapping(value = "/{id}")
    public AppointmentDTOInf getAppointmentById(@PathVariable int id) {
        return appointmentService.findAppointmentById(id);
    }

    @PostMapping(value = "/book")
    public ResponseEntity<?> bookAppointment(@RequestBody Appointment appointment
            , @RequestParam("description") String description
            , @RequestParam("time") String time) {

        appointmentService.createNewAppointment(appointment, description, time);
        return ResponseEntity.ok(appointment);
    }

    @PostMapping(value = "/cancel")
    public ResponseEntity<?> cancelAppointment(@RequestBody CancelAppointment cancelAppointment,
                                               @RequestParam("userId") int userId) {
        appointmentService.cancelAppointment(cancelAppointment, userId);
        return ResponseEntity.ok(cancelAppointment);
    }

    @GetMapping(value = "/countCancelInOneWeek")
    public Integer countCancelInOneWeek(@RequestParam("userId") int userId) {
        return appointmentService.countCancelAppointmentInOneWeek(userId);
    }

    @GetMapping(value = "/countPendingAppointment")
    public Integer countPendingAppointment(@RequestParam("userId") int userId) {
        return appointmentService.countAppointmentPendingPaymentByPatientId(userId);
    }

    @GetMapping(value = "")
    public List<Integer> listScheduleFindByDoctorAndTime(@RequestParam("doctorId") int doctorId,
                                                         @RequestParam("patientId") int patientId,
                                                         @RequestParam("time") String time) {
        return appointmentService.listScheduleFindByDoctorAndTime(doctorId, patientId, time);
    }

    @GetMapping(value = "/availableAppointment")
    public AppointmentDTOInf getCurrentAppointmentAvailable(@RequestParam("patientPhone") String patientPhone,
                                                            @RequestParam("doctorPhone") String doctorPhone,
                                                            @RequestParam("date") String date,
                                                            @RequestParam("time") String time
    ) {
        return appointmentService.getCurrentAppointmentAvailable(patientPhone, doctorPhone, date, time);
    }


    @Cacheable(value = "allCancel")
    @GetMapping(value = "/getAllCancelReason")
    public List<CancelDTOInf> getAllCancel() {
        return appointmentService.getListCancel();
    }

    @PutMapping(value = "/confirm")
    public ResponseEntity<?> confirmAppointment(@RequestParam("id") int id, @RequestBody AppointmentDetails appointmentDetails) {
        appointmentService.confirmAppointment(appointmentDetails, id);
        return ResponseEntity.ok(appointmentDetails);
    }

    @PutMapping(value = "/end")
    public ResponseEntity<?> endAppointment(@RequestParam("id") int id) {
        appointmentService.endAppointment(id);
        return ResponseEntity.ok(new ResponseOkMessage("Kết thúc phiên khám thành công", new Date()));
    }

}
