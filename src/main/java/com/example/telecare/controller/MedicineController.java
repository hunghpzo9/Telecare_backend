package com.example.telecare.controller;

import com.example.telecare.dto.MedicineDTO;
import com.example.telecare.service.impl.MedicineServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 60 * 60 * 24 * 30)
@RestController
@RequestMapping("/api/v1")
public class MedicineController {
    @Autowired
    MedicineServiceImpl medicineService;
    @GetMapping("/medicines")
    public ResponseEntity<List<MedicineDTO>> getAllVaccine(@RequestParam int index) {
        List<MedicineDTO> medicines = medicineService.getAllMedicineDTO(index);
        if (medicines.isEmpty()) {
            return new ResponseEntity<List<MedicineDTO>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<MedicineDTO>>(medicines, HttpStatus.OK);

    }

}
