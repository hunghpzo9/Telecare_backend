package com.example.telecare.service.impl;

import com.example.telecare.dto.interfaces.MedicalRecordDTOInf;
import com.example.telecare.dto.interfaces.PrescriptionDTOInf;
import com.example.telecare.dto.interfaces.PrescriptionDetailDTO;
import com.example.telecare.exception.NotFoundException;
import com.example.telecare.model.Prescription;
import com.example.telecare.repository.MedicineRepository;
import com.example.telecare.repository.PrescriptionRepository;
import com.example.telecare.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    PrescriptionRepository prescriptionRepository;
    @Autowired
    MedicineServiceImpl medicineService;
    @Autowired
    MedicineRepository medicineRepository;

    @Override
    public List<PrescriptionDTOInf> listAllPrescriptionByPatientId(int id, int page) {
        return prescriptionRepository.getAllPrescription(id, page);
    }

    @Override
    public PrescriptionDetailDTO getPrescriptionDetailByAppointmentId(int appointmentId) {
        PrescriptionDetailDTO prescriptionDetailDTO = prescriptionRepository.getPrescriptionDetailByAppointmentId(appointmentId);

        if (prescriptionDetailDTO == null) {
            throw new NotFoundException("Không tìm thấy cuộc hẹn");
        }
        return setReturnPrescriptionDetail(prescriptionDetailDTO);
    }

    @Override
    public Prescription addPrescription(Prescription prescription) {
        Prescription checkDuplicateTrace = prescriptionRepository.checkDuplicateTrace(prescription.getTrace());

        do {
            prescription.setTrace(generatePrescriptionNumber());
            System.out.println(prescription.getTrace());
        } while (checkDuplicateTrace != null);


        return prescriptionRepository.save(prescription);
    }

    @Override
    public List<PrescriptionDTOInf> getSharedPrescriptionByAppointment(int id, int page) {
        return prescriptionRepository.getSharedPrescriptionByAppointment(id,page);
    }

    private PrescriptionDetailDTO setReturnPrescriptionDetail(PrescriptionDetailDTO prescriptionDetailDTO) {
        PrescriptionDetailDTO returnPrescriptionDetailDTO = new PrescriptionDetailDTO() {
            @Override
            public Integer getId() {
                return prescriptionDetailDTO.getId();
            }

            @Override
            public String getPrescriptionDiagnosis() {
                return prescriptionDetailDTO.getPrescriptionDiagnosis();
            }

            @Override
            public String getNote() {
                return prescriptionDetailDTO.getNote();
            }

            @Override
            public String getGuardian() {
                return prescriptionDetailDTO.getGuardian();
            }

            @Override
            public String getUrl() {
                return prescriptionDetailDTO.getUrl();
            }

        };
        return returnPrescriptionDetailDTO;

    }

    protected String generatePrescriptionNumber() {
        String root = "abcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 7) { // length of the random string.
            int index = (int) (rnd.nextFloat() * root.length());
            salt.append(root.charAt(index));
        }
        String saltStr = salt.toString();
        return "TCARE" + saltStr;
    }
}
