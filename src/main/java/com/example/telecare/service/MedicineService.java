package com.example.telecare.service;

import com.example.telecare.dto.MedicineDTO;
import com.example.telecare.model.Medicine;

import java.util.List;

public interface MedicineService {
    List<Medicine> getAllMedicine(int index,String searchText);
    int getNumberOfMedicine(String searchText);
}
