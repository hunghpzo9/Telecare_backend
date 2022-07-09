package com.example.telecare.service.impl;

import com.example.telecare.dto.PrescriptionDTOInf;
import com.example.telecare.repository.PrescriptionRepository;
import com.example.telecare.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    PrescriptionRepository prescriptionRepository;

    @Override
    public List<PrescriptionDTOInf> listAllPrescriptionByPatientId(int id, int page) {
        return prescriptionRepository.getAllPrescription(id, page);
    }
}
