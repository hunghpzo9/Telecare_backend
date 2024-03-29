package com.example.telecare.dto.interfaces;

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
    String getAppointmentStatus();
    Time getStartAt();
    Time getEndAt();
    String getPaymentStatus();
    String getRelativeName();
    String getCancelReason();
    String getRefuseFillReason();
    String getCancelPerson();
    List<MedicalRecordDTOInf> getListSharedMedicalRecord();
    List<PrescriptionDTOInf> getListSharedPrescription();

}
