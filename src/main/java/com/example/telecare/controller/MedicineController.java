package com.example.telecare.controller;

import com.example.telecare.dto.MedicineNameDTO;
import com.example.telecare.model.Medicine;
import com.example.telecare.service.impl.MedicineServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 60 * 60 * 24 * 30)
@RestController
@RequestMapping("/api/v1/medicine")
public class MedicineController {
    @Autowired
    MedicineServiceImpl medicineService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllMedicine(@RequestParam int index, @RequestParam String searchText) {
        List<Medicine> medicines = medicineService.getAllMedicine(index, searchText);
        if (medicines.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(medicines, HttpStatus.OK);
    }

    @GetMapping("/getAllNameDistinct")
    public ResponseEntity<?> getAllNameDistinct(@RequestParam String searchText, @RequestParam int index) {
        List<MedicineNameDTO> medicines = medicineService.getAllMedicineNameDistinct(searchText, index);
        if (medicines.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(medicines, HttpStatus.OK);
    }

    @GetMapping("/numberOfMedicine")
    public ResponseEntity<?> getNumberOfMedicine(@RequestParam String searchText) {
        int numberOfMedicine = medicineService.getNumberOfMedicine(searchText);

        return new ResponseEntity(numberOfMedicine, HttpStatus.OK);
    }

}
