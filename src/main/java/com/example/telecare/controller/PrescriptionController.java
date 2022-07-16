package com.example.telecare.controller;

import com.example.telecare.dto.PrescriptionDTOInf;
import com.example.telecare.dto.PrescriptionDetailDTO;
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
        return prescriptionService.listAllPrescriptionByPatientId(patientId, page);
    }

    @GetMapping(value = "/getPrescriptionDetail")
    public PrescriptionDetailDTO getPrescriptionDetail(@RequestParam int appointmentId) {
        return prescriptionService.getPrescriptionDetail(appointmentId);
    }
}
