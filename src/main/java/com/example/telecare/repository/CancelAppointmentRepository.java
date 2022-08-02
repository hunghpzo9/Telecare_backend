package com.example.telecare.repository;

import com.example.telecare.dto.interfaces.CancelDTOInf;
import com.example.telecare.model.CancelAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CancelAppointmentRepository extends JpaRepository<CancelAppointment,Integer> {
    @Query(value = "SELECT cr.id,cr.name,r.name as role FROM telecare.cancel_reason cr \n" +
            "left outer join telecare.role r on cr.role_id = r.id",nativeQuery = true)
    List<CancelDTOInf> getListCancel();
}
