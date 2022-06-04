package com.example.telecare.service.impl;

import com.example.telecare.model.District;
import com.example.telecare.repository.DistrictRepository;
import com.example.telecare.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DistrictServiceImpl implements DistrictService {
    @Autowired
    DistrictRepository districtRepository;
    @Override
    public List<District> findByCityId(String cityId) {
        return districtRepository.findDistrictById(cityId);
    }
}
