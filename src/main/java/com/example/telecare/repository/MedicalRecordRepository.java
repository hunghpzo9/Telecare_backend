package com.example.telecare.repository;

import com.example.telecare.dto.MedicalRecordDTOInf;
import com.example.telecare.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Integer> {
    @Query(value = "SELECT \n" +
            "    md.id,\n" +
            "    md.medical_record_name AS medicalRecordName,\n" +
            "    u.full_name AS doctorName,\n" +
            "    md.created_at AS createdAt,\n" +
            "    md.reason, md.url," +
            "    md.main_disease AS mainDisease\n" +
            "FROM\n" +
            "    telecare.medical_record md\n" +
            "        LEFT OUTER JOIN\n" +
            "    telecare.appointment a ON md.appointment_id = a.id\n" +
            "        LEFT OUTER JOIN\n" +
            "    telecare.user u ON a.doctor_id = u.id\n" +
            "WHERE\n" +
            "    a.patient_id = ?1\n" +
            "LIMIT 5 OFFSET ?2", nativeQuery = true)
    List<MedicalRecordDTOInf> getMedicalRecordByPatientId(int id, int page);
}
