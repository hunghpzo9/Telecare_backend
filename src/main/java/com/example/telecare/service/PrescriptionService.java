package com.example.telecare.service;

import com.example.telecare.dto.PrescriptionDTOInf;

import java.util.List;

public interface PrescriptionService {
    List<PrescriptionDTOInf> listAllPrescriptionByPatientId(int id, int page);
}
