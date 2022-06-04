package com.example.telecare.model;

import javax.persistence.*;

@Entity
public class Appointment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "patient_id")
    private Integer patientId;
    @Basic
    @Column(name = "doctor_id")
    private Integer doctorId;
    @Basic
    @Column(name = "scheduele_id")
    private Integer schedueleId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getSchedueleId() {
        return schedueleId;
    }

    public void setSchedueleId(Integer schedueleId) {
        this.schedueleId = schedueleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Appointment that = (Appointment) o;

        if (id != that.id) return false;
        if (patientId != null ? !patientId.equals(that.patientId) : that.patientId != null) return false;
        if (doctorId != null ? !doctorId.equals(that.doctorId) : that.doctorId != null) return false;
        if (schedueleId != null ? !schedueleId.equals(that.schedueleId) : that.schedueleId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (patientId != null ? patientId.hashCode() : 0);
        result = 31 * result + (doctorId != null ? doctorId.hashCode() : 0);
        result = 31 * result + (schedueleId != null ? schedueleId.hashCode() : 0);
        return result;
    }
}
