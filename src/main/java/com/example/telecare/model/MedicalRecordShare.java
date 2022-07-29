package com.example.telecare.model;

import javax.persistence.*;

@Entity
@Table(name = "medical_record_share", schema = "telecare", catalog = "")
@IdClass(MedicalRecordSharePK.class)
public class MedicalRecordShare {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "appointment_id")
    private int appointmentId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "medical_record_id")
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

        MedicalRecordShare that = (MedicalRecordShare) o;

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
