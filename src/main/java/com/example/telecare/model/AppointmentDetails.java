package com.example.telecare.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "appointment_details", schema = "telecare", catalog = "")
public class AppointmentDetails {
    @Basic
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "appointment_id")
    private int appointmentId;
    @Basic
    @Column(name = "status")
    private Byte status;
    @Basic
    @Column(name = "prescription_id")
    private Integer prescriptionId;
    @Basic
    @Column(name = "medical_record_id")
    private Integer medicalRecordId;
    @Basic
    @Column(name = "status_id")
    private Integer statusId;
    @Basic
    @Column(name = "time")
    private Date time;
    @Basic
    @Column(name = "description")
    private String description;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppointmentDetails that = (AppointmentDetails) o;

        if (appointmentId != that.appointmentId) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        if (prescriptionId != null ? !prescriptionId.equals(that.prescriptionId) : that.prescriptionId != null)
            return false;
        if (medicalRecordId != null ? !medicalRecordId.equals(that.medicalRecordId) : that.medicalRecordId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = appointmentId;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (prescriptionId != null ? prescriptionId.hashCode() : 0);
        result = 31 * result + (medicalRecordId != null ? medicalRecordId.hashCode() : 0);
        return result;
    }


}
