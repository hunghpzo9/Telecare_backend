package com.example.telecare.controller;

import com.example.telecare.model.City;
import com.example.telecare.service.impl.CityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(maxAge = 60 * 60 * 24 * 30)
@RestController
@RequestMapping("/api/v1/city")
public class CityController {
    @Autowired
    CityServiceImpl cityService;

    @GetMapping(value = "")
    public List<City> getAllDoctor() {
        return cityService.findAll();
    }
}
