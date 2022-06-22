package com.example.telecare.controller;

import com.example.telecare.dto.ReportDTOInf;
import com.example.telecare.model.Report;
import com.example.telecare.service.impl.ReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 60 * 60 * 24 * 30)
@RestController
@RequestMapping("/api/v1/report")
public class ReportController {
    @Autowired
    ReportServiceImpl reportService;


    //get all report reason from database
    @Cacheable(value="allReport")
    @GetMapping(value = "/getAll")
    public List<ReportDTOInf> getAllReport() {
        return reportService.getListReport();
    }


    @PostMapping(value = "/save")
    public ResponseEntity<?> saveReport(@RequestBody Report report){
        reportService.saveReport(report);
        return  ResponseEntity.ok(report);
    }
}
