package com.example.telecare.controller;

import com.example.telecare.model.City;
import com.example.telecare.model.Ethnic;
import com.example.telecare.service.impl.EthnicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(maxAge = 60 * 60 * 24 * 30)
@RestController
@RequestMapping("/api/v1/ethnic")
public class EthnicController {
    @Autowired
    EthnicServiceImpl ethnicService;

    @Cacheable(value="allEthnic")
    @GetMapping(value = "")
    public List<Ethnic> getAllEthnic() {
        return ethnicService.findAllEthnic();
    }
}
