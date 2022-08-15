package com.example.telecare.controller;

import com.example.telecare.dto.interfaces.PrescriptionDTOInf;
import com.example.telecare.dto.interfaces.PrescriptionDetailDTO;
import com.example.telecare.exception.NotFoundException;
import com.example.telecare.model.Prescription;
import com.example.telecare.service.impl.PrescriptionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 60 * 60 * 24 * 30)
@RestController
@RequestMapping("/api/v1/prescription")
public class PrescriptionController {

    @Autowired
    PrescriptionServiceImpl prescriptionService;

    @GetMapping(value = "getAll")
    public List<PrescriptionDTOInf> findPrescriptionByPatientId(@RequestParam int patientId, @RequestParam int page) {
        if (patientId < 1) {
            throw new NotFoundException("Prescription Not found");
        } else {
            return prescriptionService.listAllPrescriptionByPatientId(patientId, page);
        }
    }

    @GetMapping(value = "/getSharedPrescriptionByAppointment")
    public List<PrescriptionDTOInf> getSharedPrescriptionByAppointment(
            @RequestParam int appointmentId, @RequestParam int page) {
        return prescriptionService.getSharedPrescriptionByAppointment(appointmentId, page);
    }


    @PostMapping(value = "/addPrescription")
    public Prescription addPrescription(@RequestBody Prescription prescription) {
        return prescriptionService.addPrescription(prescription);
    }

    @GetMapping(value = "/getPrescriptionDetail")
    public Prescription getPrescriptionDetail(@RequestParam int appointmentId) {
        return prescriptionService.getPrescriptionDetailByAppointment(appointmentId);
    }
}
