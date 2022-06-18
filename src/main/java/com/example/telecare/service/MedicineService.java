package com.example.telecare.service;

import com.example.telecare.dto.MedicineDTO;

import java.util.List;

public interface MedicineService {
    List<MedicineDTO> getAllMedicineDTO(int index);
}
