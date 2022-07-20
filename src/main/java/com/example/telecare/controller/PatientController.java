package com.example.telecare.controller;

import com.example.telecare.dto.PatientDTO;
import com.example.telecare.dto.PatientDTOInf;
import com.example.telecare.dto.PatientDTOAdminInf;
import com.example.telecare.service.impl.AddressServiceImpl;
import com.example.telecare.service.impl.PatientServiceImpl;
import com.example.telecare.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 60 * 60 * 24 * 30)
@RestController
@RequestMapping("/api/v1/patient")
public class PatientController {
    @Autowired
    PatientServiceImpl patientService;
    @Autowired
    AddressServiceImpl addressService;
    @Autowired
    UserServiceImpl userService;


    @GetMapping(value = "/{id}")
    public PatientDTOInf findPatient(@PathVariable int id) {
      return  patientService.findPatientById(id);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable("id") int id, @RequestBody PatientDTO patientDetail) {
        patientService.updatePatient(patientDetail,id);
        return ResponseEntity.ok(patientDetail);
    }
    @GetMapping(value = "")
    public PatientDTOAdminInf findPatientForAdmin(@RequestParam int id) {
        return  patientService.findPatientByIdForAdmin(id);
    }
    @GetMapping(value = "/getAll")
    public ResponseEntity<List<PatientDTOAdminInf>> getAllDoctor(@RequestParam int index, @RequestParam String searchText) {
        return new ResponseEntity<>(patientService.getAllPatient(index,searchText), HttpStatus.OK);
    }
    @GetMapping("/numberOfPatient")
    public ResponseEntity<Integer> getNumberOfDoctor(@RequestParam String searchText) {
        int medicines = patientService.getNumberOfPatient(searchText);
        return new ResponseEntity<Integer>(medicines, HttpStatus.OK);

    }

}
