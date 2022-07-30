package com.example.telecare.service;

import com.example.telecare.dto.interfaces.ReportDTOInf;
import com.example.telecare.model.Report;

import java.util.List;

public interface ReportService {
    List<ReportDTOInf> getListReport();

    void saveReport(Report report);
}
