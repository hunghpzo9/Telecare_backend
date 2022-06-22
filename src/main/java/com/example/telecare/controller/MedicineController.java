package com.example.telecare.controller;

import com.example.telecare.dto.MedicineDTO;
import com.example.telecare.model.Medicine;
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
    public ResponseEntity<List<Medicine>> getAllVaccine(@RequestParam int index) {
        List<Medicine> medicines = medicineService.getAllMedicine(index);
        if (medicines.isEmpty()) {
            return new ResponseEntity<List<Medicine>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Medicine>>(medicines, HttpStatus.OK);

    }
    @GetMapping("/numberOfMedicine")
    public ResponseEntity<Integer> getNumberOfMedicine() {
        int medicines = medicineService.getNumberOfMedicine();

        return new ResponseEntity<Integer>(medicines, HttpStatus.OK);

    }

}
