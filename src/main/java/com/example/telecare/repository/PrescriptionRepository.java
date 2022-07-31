package com.example.telecare.repository;

import com.example.telecare.dto.interfaces.MedicalRecordDTOInf;
import com.example.telecare.dto.interfaces.PrescriptionDTOInf;
import com.example.telecare.dto.interfaces.PrescriptionDetailDTO;
import com.example.telecare.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {

    @Query(value = "SELECT \n" +
            "    pre.id,\n" +
            "    pre.diagnosis AS prescriptionDiagnosis,\n" +
            "    u.full_name AS doctorName,\n" +
            "    pre.created_at AS createdAt  ,\n" +
            "    pre.url\n" +
            "FROM\n" +
            "    telecare.prescription pre\n" +
            "        LEFT OUTER JOIN\n" +
            "    telecare.appointment a ON pre.appointment_id = a.id\n" +
            "        LEFT OUTER JOIN\n" +
            "    telecare.user u ON a.doctor_id = u.id\n" +
            "WHERE\n" +
            "    a.patient_id = ?1\n" +
            "LIMIT 5 OFFSET ?2", nativeQuery = true)
    List<PrescriptionDTOInf> getAllPrescription(int id, int page);

    @Query(value = "SELECT  pre.id, pre.diagnosis AS prescriptionDiagnosis, u.full_name AS doctorName, pre.created_at AS createdAt , pre.url\n" +
            "FROM telecare.prescription pre\n" +
            "LEFT OUTER JOIN appointment a on pre.appointment_id = a.id\n" +
            "LEFT OUTER JOIN telecare.medical_record m ON m.appointment_id = pre.appointment_id\n" +
            "LEFT OUTER JOIN telecare.user u ON a.doctor_id = u.id\n" +
            "LEFT OUTER JOIN telecare.medical_record_share mds ON mds.medical_record_id = m.id\n" +
            "where mds.appointment_id = ?1\n" +
            "LIMIT 5 OFFSET ?2", nativeQuery = true)
    List<PrescriptionDTOInf> getSharedPrescriptionByAppointment(int id, int page);

    @Query(value = "SELECT \n" +
            "    id,\n" +
            "    diagnosis AS prescriptionDiagnosis,\n" +
            "    note,\n" +
            "    guardian,\n" +
            "    url\n" +
            "FROM\n" +
            "    telecare.prescription\n" +
            "WHERE\n" +
            "    appointment_id = ?1", nativeQuery = true)
    PrescriptionDetailDTO getPrescriptionDetailByAppointmentId(int id);

    @Query(value = "SELECT * FROM telecare.prescription WHERE trace = ?1",
            nativeQuery = true)
    Prescription checkDuplicateTrace(String trace);

    @Query(value = "SELECT * FROM telecare.prescription WHERE appointment_id = ?1",
            nativeQuery = true)
    Prescription findPrescriptionByAppointmentId(int id);
}
