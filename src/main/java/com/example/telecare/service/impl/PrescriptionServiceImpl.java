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

    private PrescriptionDTOInf setReturnPrescription(PrescriptionDTOInf prescriptionDTOInf) {

        PrescriptionDTOInf returnDtoInf = new PrescriptionDTOInf() {

            @Override
            public Integer getId() {
                return prescriptionDTOInf.getId();
            }

            @Override
            public String getPrescriptionDiagnosis() {
                return prescriptionDTOInf.getPrescriptionDiagnosis();
            }

            @Override
            public String getDoctorName() {
                return prescriptionDTOInf.getDoctorName();
            }

            @Override
            public Date getCreatedAt() {
                return prescriptionDTOInf.getCreatedAt();
            }

            @Override
            public String getUrl() {
                return prescriptionDTOInf.getUrl();
            }

        };
        return returnDtoInf;
    }

    @Override
    public List<PrescriptionDTOInf> listAllPrescriptionByPatientId(int id, int page) {
        List<PrescriptionDTOInf> prescriptionList = prescriptionRepository.getAllPrescription(id, page);
        List<PrescriptionDTOInf> returnPrescriptionList = new ArrayList<>();
        for (PrescriptionDTOInf prescriptionDTOInf : prescriptionList) {
            PrescriptionDTOInf finalPrescriptionList = prescriptionDTOInf;
            prescriptionDTOInf = setReturnPrescription(finalPrescriptionList);
            returnPrescriptionList.add(prescriptionDTOInf);
        }
        return returnPrescriptionList;
    }
}
