package com.example.telecare.service;

import com.example.telecare.dto.MedicineNameDTO;
import com.example.telecare.model.Medicine;

import java.util.List;

public interface MedicineService {
    List<Medicine> getAllMedicine(int index,String searchText);

    List<MedicineNameDTO> getAllMedicineNameDistinct(String searchText);

    int getNumberOfMedicine(String searchText);
}
