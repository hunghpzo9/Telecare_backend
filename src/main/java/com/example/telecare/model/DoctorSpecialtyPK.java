package com.example.telecare.model;

import javax.persistence.*;
import java.io.Serializable;

public class DoctorSpecialtyPK implements Serializable {
    @Column(name = "doctor_id")
    @Basic
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer doctorId;
    @Column(name = "specialty_id")
    @Basic
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer specialtyId;

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(Integer specialtyId) {
        this.specialtyId = specialtyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DoctorSpecialtyPK that = (DoctorSpecialtyPK) o;

        if (doctorId != null ? !doctorId.equals(that.doctorId) : that.doctorId != null) return false;
        if (specialtyId != null ? !specialtyId.equals(that.specialtyId) : that.specialtyId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = doctorId != null ? doctorId.hashCode() : 0;
        result = 31 * result + (specialtyId != null ? specialtyId.hashCode() : 0);
        return result;
    }
}
