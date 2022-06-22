package com.example.telecare.service.impl;

import com.example.telecare.dto.MedicineDTO;
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
    public List<Medicine> getAllMedicine(int index) {
        return medicineRepository.getAllMedicine(index);
    }

    @Override
    public int getNumberOfMedicine() {
        return medicineRepository.getNumberOfMedicine();
    }

}
