package com.example.telecare.repository;

import com.example.telecare.dto.interfaces.ReportDTOInf;

import com.example.telecare.dto.interfaces.ReportDTOInfForAdmin;
import com.example.telecare.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report,Integer>{
    @Query(value = "SELECT rr.id,rr.name,r.name as role FROM telecare.report_reason rr " +
            "left outer join telecare.role r on rr.role_id = r.id; ",nativeQuery = true)
    List<ReportDTOInf> getListReport();
    @Query(value = "SELECT r.id,up.full_name patientName,up.id patientId,ud.full_name doctorName,ud.id doctorId,r.appointment_id appointmentId,r.description,rr.name reason,r.created_at time,r.status_id statusId,rs.status statusName FROM telecare.report r left join appointment a on r.appointment_id=a.id\n" +
            " left join user up on a.patient_id = up.id\n" +
            " left join user ud on a.doctor_id=ud.id\n" +
            " left join report_reason rr on r.reason_id=rr.id\n" +
            " left join report_status rs on r.status_id=rs.id\n" +
            " where up.full_name like %?2% or ud.full_name like %?2% or r.appointment_id like %?2% or r.created_at like %?2% or rs.status like %?2%\n" +
            " order by r.created_at desc\n" +
            " limit ?1,10",nativeQuery = true)
    List<ReportDTOInfForAdmin> getListReportForAdmin(int index,String search);
    @Query(value = "SELECT count(*) FROM telecare.report r left join appointment a on r.appointment_id=a.id\n" +
            " left join user up on a.patient_id = up.id\n" +
            " left join user ud on a.doctor_id=ud.id\n" +
            " left join report_reason rr on r.reason_id=rr.id\n" +
            " left join report_status rs on r.status_id=rs.id\n" +
            " where up.full_name like %?1% or ud.full_name like %?1% or r.appointment_id like %?1% or r.created_at like %?1% or rs.status like %?1%",nativeQuery = true)
    int getNumberOfReportForAdmin(String search);
}
