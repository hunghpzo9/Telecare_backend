package com.example.telecare.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
public class Prescription {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "appointment_id")
    private Integer appointmentId;
    @Basic
    @Column(name = "diagnosis")
    private String diagnosis;
    @Basic
    @Column(name = "note")
    private String note;
    @Basic
    @Column(name = "medicine_id")
    private Integer medicineId;
    @Basic
    @Column(name = "guardian")
    private String guardian;
    @Basic
    @CreationTimestamp
    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;
    @Basic
    @UpdateTimestamp
    @Column(name = "updated_at")
    @UpdateTimestamp
    private Timestamp updatedAt;
    @Basic
    @Column(name = "prescription_medicine_id")
    private String prescriptionMedicineId;
    @Basic
    @Column(name = "url")
    private String url;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Prescription that = (Prescription) o;

        if (id != that.id) return false;
        if (appointmentId != null ? !appointmentId.equals(that.appointmentId) : that.appointmentId != null)
            return false;
        if (diagnosis != null ? !diagnosis.equals(that.diagnosis) : that.diagnosis != null) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;
        if (medicineId != null ? !medicineId.equals(that.medicineId) : that.medicineId != null) return false;
        if (guardian != null ? !guardian.equals(that.guardian) : that.guardian != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (appointmentId != null ? appointmentId.hashCode() : 0);
        result = 31 * result + (diagnosis != null ? diagnosis.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (medicineId != null ? medicineId.hashCode() : 0);
        result = 31 * result + (guardian != null ? guardian.hashCode() : 0);
        return result;
    }

}
