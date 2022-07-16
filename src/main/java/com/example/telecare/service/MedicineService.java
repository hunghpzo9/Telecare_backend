package com.example.telecare.service;

import com.example.telecare.dto.MedicinePrescriptionDTO;
import com.example.telecare.model.Medicine;

import java.util.List;

public interface MedicineService {
    List<Medicine> getAllMedicine(int index,String searchText);

    List<MedicinePrescriptionDTO> findAllMedicineByPrescriptionId(int id);

    int getNumberOfMedicine(String searchText);
}
