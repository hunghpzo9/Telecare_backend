package com.example.telecare.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

public class MedicalRecordSharePK implements Serializable {
    @Column(name = "appointment_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appointmentId;
    @Column(name = "medical_record_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int medicalRecordId;

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getMedicalRecordId() {
        return medicalRecordId;
    }

    public void setMedicalRecordId(int medicalRecordId) {
        this.medicalRecordId = medicalRecordId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MedicalRecordSharePK that = (MedicalRecordSharePK) o;

        if (appointmentId != that.appointmentId) return false;
        if (medicalRecordId != that.medicalRecordId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = appointmentId;
        result = 31 * result + medicalRecordId;
        return result;
    }
}
