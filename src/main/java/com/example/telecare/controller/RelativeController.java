package com.example.telecare.controller;

import com.example.telecare.model.Relative;
import com.example.telecare.service.impl.RelativeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 60 * 60 * 24 * 30)
@RestController
@RequestMapping("/api/v1/relative")
public class RelativeController {

    @Autowired
    RelativeServiceImpl relativeService;

    @GetMapping(value = "/patientId={id}")
    public List<Relative> getAllRelative(@PathVariable int id) {
        return relativeService.findAllRelativeByPatientId(id);
    }

    @PostMapping("/addNew")
    public Relative addRelative(@RequestBody Relative relative){
        Relative addNewRelative =  relativeService.addRelative(relative);
        return  addNewRelative;
    }
}
