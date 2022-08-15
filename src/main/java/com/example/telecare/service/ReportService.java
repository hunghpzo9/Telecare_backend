package com.example.telecare.service;

import com.example.telecare.dto.interfaces.ReportDTOInf;
import com.example.telecare.dto.interfaces.ReportDTOInfForAdmin;
import com.example.telecare.model.Report;

import java.util.List;

public interface ReportService {
    List<ReportDTOInf> getListReport();
    List<ReportDTOInfForAdmin> getListReportForAdmin(int index,String search);
    void saveReport(Report report);
    int getNumberOfReportForAdmin(String search);
    void updateStatus(int reportId, int statusId);
}
