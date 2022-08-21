package com.example.telecare.repository;

import com.example.telecare.dto.interfaces.CancelDTOInf;
import com.example.telecare.dto.interfaces.CancelReasonDTOInf;
import com.example.telecare.model.CancelAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CancelAppointmentRepository extends JpaRepository<CancelAppointment,Integer> {
    @Query(value = "SELECT cr.id,cr.name,r.name as role FROM telecare.cancel_reason cr \n" +
            "left outer join telecare.role r on cr.role_id = r.id",nativeQuery = true)
    List<CancelDTOInf> getListCancel();

    @Query(value = "SELECT CASE WHEN cr.role_id != 3 Then u.full_name ELSE  cr.name End as cancelUser\n" +
            ",ca.description as cancelDescription,\n" +
            "CASE WHEN cr.role_id != 3 Then cr.name End as cancelReason\n" +
            "FROM telecare.cancel_appointment ca \n" +
            "LEFT OUTER JOIN telecare.user u on ca.user_id = u.id\n" +
            "LEFT OUTER JOIN telecare.cancel_reason cr on cr.id = ca.cancel_reason_id\n" +
            "WHERE ca.appointment_id = ?1",nativeQuery = true)
    CancelReasonDTOInf getCancelDetailByAppointment(int appointmentId);
}
