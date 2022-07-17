package com.example.telecare.controller;

import com.example.telecare.dto.PrescriptionDTOInf;
import com.example.telecare.dto.PrescriptionDetailDTO;
import com.example.telecare.dto.ResponseOkMessage;
import com.example.telecare.model.Prescription;
import com.example.telecare.service.impl.PrescriptionServiceImpl;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
        return prescriptionService.getPrescriptionDetailByAppointmentId(appointmentId);
    }

    @PutMapping(value = "/addPrescription")
    public Prescription addPrescription(@RequestBody Prescription prescription) {
        return prescriptionService.addPrescription(prescription);
    }

    @PutMapping(value = "/updatePrescription")
    public ResponseEntity<Prescription> updatePrescription(@RequestBody Prescription prescription, @RequestParam int appointmentId) {
        prescriptionService.updatePrescription(prescription, appointmentId);
        return ResponseEntity.ok(prescription);
    }

    @PutMapping(value = "/addMedicine")
    public ResponseEntity<?> addMedicine(@RequestParam int prescriptionId, @RequestParam int medicineId) {
        prescriptionService.addMedicineForPrescription(prescriptionId, medicineId);
        return ResponseEntity.ok(new ResponseOkMessage("Add successful", new Date()));
    }

    @DeleteMapping(value = "/removeMedicine")
    public ResponseEntity<?> removeMedicine(@RequestParam int prescriptionId, @RequestParam int medicineId) {
        prescriptionService.removeMedicineOfPrescription(prescriptionId, medicineId);
        return ResponseEntity.ok(new ResponseOkMessage("Remove successfully", new Date()));
    }
}
