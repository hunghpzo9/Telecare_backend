package com.example.telecare.service.impl;

import com.example.telecare.dto.interfaces.ReportDTOInf;
import com.example.telecare.model.Report;
import com.example.telecare.repository.ReportRepository;
import com.example.telecare.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    ReportRepository reportRepository;
    @Override
    public List<ReportDTOInf> getListReport() {
        return reportRepository.getListReport();
    }

    @Override
    public void saveReport(Report report) {
         reportRepository.save(report);
    }
}
