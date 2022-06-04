package com.example.telecare.service;

import com.example.telecare.model.Ward;

import java.util.List;

public interface WardService {
    List<Ward> findByDistrictId(String id);
}
