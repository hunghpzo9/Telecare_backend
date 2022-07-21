package com.example.telecare.service.impl;

import com.example.telecare.dto.MedicineDTO;
import com.example.telecare.dto.MedicineNameDTO;
import com.example.telecare.dto.MedicinePrescriptionDTO;
import com.example.telecare.model.Medicine;
import com.example.telecare.repository.MedicineRepository;
import com.example.telecare.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineServiceImpl implements MedicineService {
    @Autowired
    MedicineRepository medicineRepository;

    @Override
    public List<Medicine> getAllMedicine(int index, String searchText) {
        return medicineRepository.getAllMedicine(index, searchText);
    }

    @Override
    public List<MedicineNameDTO> getAllMedicineNameDistinct(String searchText, int index) {
        return medicineRepository.getAllMedicineNameDistinct(searchText, index);
    }

    @Override
    public int getNumberOfMedicine(String searchText) {
        return medicineRepository.getNumberOfMedicine(searchText);
    }

}
