package com.example.telecare.controller;

import com.example.telecare.dto.DoctorDTOInf;
import com.example.telecare.service.impl.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 60 * 60 * 24 * 30)
@RestController
@RequestMapping("/api/v1/doctor")
public class DoctorController {
    @Autowired
    DoctorServiceImpl doctorService;

    @GetMapping(value = "/{id}")
    @Cacheable(key = "#id", value = "doctorDetail{id}")
    public DoctorDTOInf findDoctor(@PathVariable int id) {
        return doctorService.findDoctorById(id);
    }

    @GetMapping(value = "/search={search}/pageNo={pageNo}")
    public List<DoctorDTOInf> findDoctor(@PathVariable String search, @PathVariable int pageNo) {
        return doctorService.listAllDoctor("%" + search + "%", pageNo);
    }

    @GetMapping(value = "/search={search}/{specialtyId}/pageNo={pageNo}")
    public List<DoctorDTOInf> findDoctorBySpecialty(@PathVariable String search, @Param("specialtyId") List<Integer> specialtyId, @PathVariable int pageNo) {
        return doctorService.listAllDoctorBySpecialty("%" + search + "%", specialtyId, pageNo);
    }
}
