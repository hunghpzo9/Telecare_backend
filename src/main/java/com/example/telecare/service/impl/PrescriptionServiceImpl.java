package com.example.telecare.service.impl;

import com.example.telecare.dto.DoctorAchievementDTO;
import com.example.telecare.dto.MedicinePrescriptionDTO;
import com.example.telecare.dto.PrescriptionDTOInf;
import com.example.telecare.dto.PrescriptionDetailDTO;
import com.example.telecare.exception.NotFoundException;
import com.example.telecare.model.Medicine;
import com.example.telecare.repository.PrescriptionRepository;
import com.example.telecare.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    PrescriptionRepository prescriptionRepository;
    @Autowired
    MedicineServiceImpl medicineService;

    @Override
    public List<PrescriptionDTOInf> listAllPrescriptionByPatientId(int id, int page) {
        return prescriptionRepository.getAllPrescription(id, page);
    }

    @Override
    public PrescriptionDetailDTO getPrescriptionDetail(int appointmentId) {
        PrescriptionDetailDTO prescriptionDetailDTO = prescriptionRepository.getPrescriptionDetailByAppointmentId(appointmentId);

        if (prescriptionDetailDTO == null) {
            throw new NotFoundException("Không tìm thấy cuộc hẹn");
        }
        return setReturnPrescriptionDetail(prescriptionDetailDTO);
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

            @Override
            public List<MedicinePrescriptionDTO> getListMedicine() {
                List<MedicinePrescriptionDTO> medicines = new ArrayList<>();
                for (MedicinePrescriptionDTO m : medicineService.findAllMedicineByPrescriptionId(prescriptionDetailDTO.getId())) {
                    medicines.add(m);
                }
                return medicines;
            }
        };
        return returnPrescriptionDetailDTO;

    }
    protected String generateMedicalRecordNumber() {
        String root = "abcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 8) { // length of the random string.
            int index = (int) (rnd.nextFloat() * root.length());
            salt.append(root.charAt(index));
        }
        String saltStr = salt.toString();
        return "TCARE"+saltStr;
    }
}
