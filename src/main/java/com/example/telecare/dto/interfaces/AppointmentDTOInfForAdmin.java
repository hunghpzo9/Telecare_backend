package com.example.telecare.dto.interfaces;

import com.example.telecare.model.MedicalRecord;
import com.example.telecare.model.Prescription;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public interface AppointmentDTOInfForAdmin {
    Integer getId();
    Integer getPatientId();
    String getPatientName();
    String getPatientPhone();
    String getDoctorName();
    Integer getDoctorId();
    String getPrescriptionTrace();
    String getPrescriptionUrl();
    String getMedicalRecordTrace();
    String getMedicalRecordUrl();
    Date getTime();

    Time getStartAt();
    Time getEndAt();
    String getPaymentStatus();
    String getRelativeName();
    List<MedicalRecordDTOInf> getListSharedMedicalRecord();
    List<PrescriptionDTOInf> getListSharedPrescription();

}
