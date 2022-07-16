package com.example.telecare.repository;

import com.example.telecare.dto.MedicalRecordDTOInf;
import com.example.telecare.dto.MedicalRecordDetailDTO;
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

    @Query(value = "SELECT \n" +
            "    id,\n" +
            "    medical_record_name AS medicalRecordName,\n" +
            "    reason,\n" +
            "    main_disease AS mainDisease,\n" +
            "    url,\n" +
            "    type,\n" +
            "    external,\n" +
            "    guardian_detail AS guardianDetail,\n" +
            "    guardian_phone as guardianPhone,\n" +
            "    medical_process AS medicalProcess,\n" +
            "    self_history AS selfHistory,\n" +
            "    family_history AS familyHistory,\n" +
            "    body_examination as bodyExamination,\n" +
            "    organs_examination as organsExamination,\n" +
            "    summary,\n" +
            "    include_disease AS includeDisease,\n" +
            "    first_amount AS firstAmount,\n" +
            "    second_amount AS secondAmount,\n" +
            "    is_edited AS isEdited\n" +
            "FROM\n" +
            "    telecare.medical_record\n" +
            "WHERE\n" +
            "    appointment_id = ?1", nativeQuery = true)
    MedicalRecordDetailDTO getMedicalRecordDetailsByAppointmentId(int id);

    @Query(value = "SELECT * FROM telecare.medical_record where created_at < (DATE_ADD(?1, INTERVAL -43200 SECOND)) " +
            "and  is_edited = 0 \n"
            , nativeQuery = true)
    List<MedicalRecord> getOverDueMedicalRecord(String date);

    @Query(value = "SELECT * FROM telecare.medical_record where appointment_id = ?1", nativeQuery = true)
    MedicalRecord findMedicalRecordByAppointmentId(int id);
}
