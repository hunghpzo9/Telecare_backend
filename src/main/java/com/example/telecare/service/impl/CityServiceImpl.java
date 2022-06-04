package com.example.telecare.service.impl;

import com.example.telecare.model.City;
import com.example.telecare.repository.CityRepository;
import com.example.telecare.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    @Autowired
    CityRepository cityRepository;

    @Override
    public List<City> findAll() {
        return cityRepository.findAll();
    }
}
