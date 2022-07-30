package com.example.telecare.repository;

import com.example.telecare.dto.interfaces.ReportDTOInf;

import com.example.telecare.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report,Integer>{
    @Query(value = "SELECT rr.id,rr.name,r.name as role FROM telecare.report_reason rr " +
            "left outer join telecare.role r on rr.role_id = r.id; ",nativeQuery = true)
    List<ReportDTOInf> getListReport();
}
