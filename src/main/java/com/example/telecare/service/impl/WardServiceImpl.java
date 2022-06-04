package com.example.telecare.service.impl;

import com.example.telecare.model.Ward;
import com.example.telecare.repository.WardRepository;
import com.example.telecare.service.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WardServiceImpl implements WardService {
    @Autowired
    WardRepository wardRepository;
    @Override
    public List<Ward> findByDistrictId(String id) {
        return wardRepository.findWardById(id);
    }
}
