package com.example.telecare.service.impl;

import com.example.telecare.dto.interfaces.PrescriptionDTOInf;
import com.example.telecare.exception.BadRequestException;
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
        if (page < 0) {
            throw new NotFoundException("Prescription not found! Index can not less than 0");
        }
        if (id < 1) {
            throw new NotFoundException("Prescription not found! Patient ID is incorrect");
        }
        try {
            return prescriptionRepository.getAllPrescription(id, page);
        } catch (Exception e) {
            throw new NotFoundException("Prescription not found! Patient id does not exist");
        }
    }


    @Override
    public Prescription addPrescription(Prescription prescription) {
        if (prescription.getAppointmentId() < 1) {
            throw new BadRequestException("Add fail! AppointmentID is in correct");
        }
        try {
            Prescription checkDuplicateTrace = prescriptionRepository.checkDuplicateTrace(prescription.getTrace());
            do {
                prescription.setTrace(generatePrescriptionNumber());
                System.out.println(prescription.getTrace());
            } while (checkDuplicateTrace != null);

            return prescriptionRepository.save(prescription);
        } catch (Exception e) {
            throw new NotFoundException("Add fail! Make sure your data is correct");
        }
    }

    @Override
    public List<PrescriptionDTOInf> getSharedPrescriptionByAppointment(int id, int page) {
        return prescriptionRepository.getSharedPrescriptionByAppointment(id, page);
    }

    @Override
    public Prescription getPrescriptionDetailByAppointment(int appointmentId) {
        return prescriptionRepository.getPrescriptionDetailByAppointment(appointmentId);
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
