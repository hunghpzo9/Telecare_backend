package com.example.telecare.service.impl;

import com.example.telecare.dto.interfaces.MedicineNameDTO;
import com.example.telecare.exception.NotFoundException;
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
        if(index < 0){
            throw new NotFoundException("Medicine not found! Index can not less than 0");
        }else{
            return medicineRepository.getAllMedicineNameDistinct(searchText, index);
        }
    }

    @Override
    public int getNumberOfMedicine(String searchText) {
        return medicineRepository.getNumberOfMedicine(searchText);
    }

    @Override
    public void updateMedicineStatus(int medicineId, Byte status) {
        Medicine medicine = medicineRepository.findById(medicineId).orElseThrow(()
                -> new NotFoundException("Không tìm thấy thuốc"));
        medicine.setStatus(status);
        medicineRepository.save(medicine);
    }

}
