package com.example.telecare.service;

import com.example.telecare.dto.interfaces.PrescriptionDTOInf;
import com.example.telecare.dto.interfaces.PrescriptionDetailDTO;
import com.example.telecare.model.Prescription;

import java.util.List;

public interface PrescriptionService {
    List<PrescriptionDTOInf> listAllPrescriptionByPatientId(int id, int page);

    Prescription addPrescription(Prescription prescription);

    List<PrescriptionDTOInf> getSharedPrescriptionByAppointment(int id, int page);

    Prescription getPrescriptionDetailByAppointment(int appointmentId);

}
