package com.example.telecare.service;

import com.example.telecare.model.Relative;
import com.example.telecare.repository.RelativeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface RelativeService {
    List<Relative> findAllRelativeByPatientId(int patientId);

    Relative addRelative(Relative relative);

    Relative findRelativeById(int id);

    void updateRelativeById(Relative relativeDetail, int id);
}
