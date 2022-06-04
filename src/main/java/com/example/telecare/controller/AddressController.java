package com.example.telecare.controller;

import com.example.telecare.model.City;
import com.example.telecare.model.District;
import com.example.telecare.model.Ward;
import com.example.telecare.service.DistrictService;
import com.example.telecare.service.impl.CityServiceImpl;
import com.example.telecare.service.impl.DistrictServiceImpl;
import com.example.telecare.service.impl.WardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
