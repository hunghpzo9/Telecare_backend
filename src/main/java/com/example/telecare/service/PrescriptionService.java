package com.example.telecare.service;

import com.example.telecare.dto.PrescriptionDTOInf;
import com.example.telecare.dto.PrescriptionDetailDTO;
import com.example.telecare.model.Prescription;

import java.util.List;

public interface PrescriptionService {
    List<PrescriptionDTOInf> listAllPrescriptionByPatientId(int id, int page);

    PrescriptionDetailDTO getPrescriptionDetailByAppointmentId(int id);

    Prescription addPrescription(Prescription prescription);

}
