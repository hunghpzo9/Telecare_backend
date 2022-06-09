package com.example.telecare.controller;

import com.example.telecare.model.*;
import com.example.telecare.service.DistrictService;
import com.example.telecare.service.impl.AddressServiceImpl;
import com.example.telecare.service.impl.CityServiceImpl;
import com.example.telecare.service.impl.DistrictServiceImpl;
import com.example.telecare.service.impl.WardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 60 * 60 * 24 * 30)
@RestController
@RequestMapping("/api/v1/address")
public class AddressController {
    @Autowired
    CityServiceImpl cityService;
    @Autowired
    DistrictServiceImpl districtService;
    @Autowired
    WardServiceImpl wardService;
    @Autowired
    AddressServiceImpl addressService;

    @Cacheable(value="allCity")
    @GetMapping(value = "/city")
    public List<City> getAllCity() {
        return cityService.findAll();
    }

    @GetMapping(value = "/district/cityId={id}")
    public List<District> getDistrict(@PathVariable String id) {
        return districtService.findByCityId(id);
    }

    @GetMapping(value = "/ward/districtId={id}")
    public List<Ward> getWard(@PathVariable String id) {
        return wardService.findByDistrictId(id);
    }
}
