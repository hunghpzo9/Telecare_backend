package com.example.telecare.service;

import com.example.telecare.model.District;

import java.util.List;


public interface DistrictService {
    List<District> findByCityId(String cityId);
}
