package com.example.telecare.service.impl;

import com.example.telecare.exception.NotFoundException;
import com.example.telecare.model.Ethnic;
import com.example.telecare.repository.EthnicRepository;
import com.example.telecare.service.EthnicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EthnicServiceImpl implements EthnicService {
    @Autowired
    EthnicRepository ethnicRepository;
    @Override
    public List<Ethnic> findAllEthnic() {
        return ethnicRepository.findAll();
    }

    @Override
    public Ethnic findEthnicById(int id) {
        return ethnicRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Not found ethnic"));
    }
}
