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
@Table(name = "medical_record", schema = "telecare", catalog = "")
public class MedicalRecord {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "url")
    private String url;
    @Basic
    @Column(name = "appointment_id")
    private int appointmentId;
    @Basic
    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;
    @Basic
    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;
    @Basic
    @Column(name = "medical_record_name")
    private String medicalRecordName;
    @Basic
    @Column(name = "subject")
    private String subject;
    @Basic
    @Column(name = "guardian_detail")
    private String guardianDetail;
    @Basic
    @Column(name = "guardian_phone")
    private String guardianPhone;
    @Basic
    @Column(name = "reason")
    private String reason;
    @Basic
    @Column(name = "medical_process")
    private String medicalProcess;
    @Basic
    @Column(name = "self_history")
    private String selfHistory;
    @Basic
    @Column(name = "family_history")
    private String familyHistory;
    @Basic
    @Column(name = "body_examination")
    private String bodyExamination;
    @Basic
    @Column(name = "organs_examination")
    private String organsExamination;
    @Basic
    @Column(name = "summary")
    private String summary;
    @Basic
    @Column(name = "main_disease")
    private String mainDisease;
    @Basic
    @Column(name = "include_disease")
    private String includeDisease;
    @Basic
    @Column(name = "first_amount")
    private String firstAmount;
    @Basic
    @Column(name = "second_amount")
    private String secondAmount;
    @Basic
    @Column(name = "is_edited")
    private Byte isEdited;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MedicalRecord that = (MedicalRecord) o;

        if (id != that.id) return false;
        if (appointmentId != that.appointmentId) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + appointmentId;
        return result;
    }
}
