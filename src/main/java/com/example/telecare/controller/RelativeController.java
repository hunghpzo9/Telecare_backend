package com.example.telecare.controller;

import com.example.telecare.exception.NotFoundException;
import com.example.telecare.model.Relative;
import com.example.telecare.repository.RelativeRepository;
import com.example.telecare.service.impl.RelativeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 60 * 60 * 24 * 30)
@RestController
@RequestMapping("/api/v1/relative")
public class RelativeController {

    @Autowired
    RelativeServiceImpl relativeService;

    @Autowired
    RelativeRepository relativeRepository;

    @GetMapping(value = "/patientId={id}")
    public List<Relative> getAllRelative(@PathVariable int id) {
        if (id < 1) {
            throw new NotFoundException("Relative Not found");
        } else {
            return relativeService.findAllRelativeByPatientId(id);
        }
    }

    @GetMapping(value = "/{id}")
    public Relative getRelativeById(@PathVariable int id) {
        if (id < 1) {
            throw new NotFoundException("Relative Not found");
        } else {
            return relativeService.findRelativeById(id);
        }
    }

    @PostMapping("/addNew")
    public Relative addRelative(@RequestBody Relative relative) {
        Relative addNewRelative = relativeService.addRelative(relative);
        return addNewRelative;
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Relative> updateRelative(@RequestBody Relative relative, @PathVariable int id) {
        relativeService.updateRelativeById(relative, id);
        return ResponseEntity.ok(relative);
    }

}
