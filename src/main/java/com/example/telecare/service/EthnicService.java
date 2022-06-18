package com.example.telecare.service;


import com.example.telecare.model.Ethnic;

import java.util.List;

public interface EthnicService {
    List<Ethnic> findAllEthnic();

    Ethnic findEthnicById(int id);
}
