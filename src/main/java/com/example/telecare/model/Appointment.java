package com.example.telecare.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
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
    @Basic
    @Column(name = "relative_id")
    private Integer relativeId;
    @Basic
    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;
    @Basic
    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;



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
