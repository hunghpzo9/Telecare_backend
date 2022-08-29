package com.example.telecare.repository;

import com.example.telecare.dto.interfaces.MedicalRecordDTOInf;
import com.example.telecare.dto.interfaces.MedicalRecordDetailDTO;
import com.example.telecare.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
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
            "    a.patient_id = ?1 order by id desc\n" +
            "LIMIT 5 OFFSET ?2", nativeQuery = true)
    List<MedicalRecordDTOInf> getMedicalRecordByPatientId(int id, int page);

    @Query(value = "SELECT \n" +
            "                md.id,\n" +
            "                md.medical_record_name AS medicalRecordName,\n" +
            "                u.full_name AS doctorName,\n" +
            "                md.created_at AS createdAt,\n" +
            "                md.reason, md.url,\n" +
            "                md.main_disease AS mainDisease\n" +
            "              " +
            "            FROM\n" +
            "                telecare.medical_record md\n" +
            "                    LEFT OUTER JOIN\n" +
            "                telecare.appointment a ON md.appointment_id = a.id\n" +
            "                    LEFT OUTER JOIN\n" +
            "                telecare.user u ON a.doctor_id = u.id\n" +
            "            WHERE\n" +
            "                a.patient_id = ?1 and if (?3,a.relative_id = ?4,a.relative_id IS NULL)\n" +
            "            LIMIT 5 OFFSET ?2", nativeQuery = true)
    List<MedicalRecordDTOInf> getShareMedicalRecord(int id, int page, boolean isRelative, int relativeId);

    @Query(value = "SELECT  md.id,\n" +
            "                md.medical_record_name AS medicalRecordName,\n" +
            "                u.full_name AS doctorName,\n" +
            "                md.created_at AS createdAt,\n" +
            "                md.reason, md.url,\n" +
            "                md.main_disease AS mainDisease FROM telecare.medical_record_share mds\n" +
            "                LEFT OUTER JOIN\n" +
            "telecare.medical_record md on mds.medical_record_id = md.id \n" +
            "                    LEFT OUTER JOIN\n" +
            "                telecare.appointment a ON md.appointment_id = a.id\n" +
            "                    LEFT OUTER JOIN\n" +
            "telecare.user u ON a.doctor_id = u.id\n" +
            "WHERE\n" +
            "mds.appointment_id = ?1\n" +
            "LIMIT 5 OFFSET ?2", nativeQuery = true)
    List<MedicalRecordDTOInf> getSharedMedicalRecordByAppointment(int id, int page);

    @Query(value = "SELECT   md.id,\n" +
            "                md.medical_record_name AS medicalRecordName,\n" +
            "                u.full_name AS doctorName,md.appointment_id AS appointmentId,\n" +
            "                md.created_at AS createdAt,\n" +
            "                md.reason, md.url,\n" +
            "                md.main_disease AS mainDisease FROM telecare.medical_record_share mds\n" +
            "                LEFT OUTER JOIN\n" +
            "telecare.medical_record md on mds.medical_record_id = md.id \n" +
            "                    LEFT OUTER JOIN\n" +
            "                telecare.appointment a ON md.appointment_id = a.id\n" +
            "                    LEFT OUTER JOIN\n" +
            "telecare.user u ON a.doctor_id = u.id\n" +
            "WHERE\n" +
            "mds.appointment_id = ?1\n" +
            "", nativeQuery = true)
    List<MedicalRecordDTOInf> getSharedMedicalRecordByAppointmentAdmin(int id);

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

    @Query(value = "SELECT * FROM telecare.medical_record WHERE trace = ?1",
            nativeQuery = true)
    MedicalRecord checkDuplicateTrace(String trace);

    @Query(value = "SELECT * FROM telecare.medical_record where appointment_id = ?1", nativeQuery = true)
    MedicalRecord findMedicalRecordByAppointmentId(int id);
}
