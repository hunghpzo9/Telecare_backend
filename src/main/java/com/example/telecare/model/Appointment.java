package com.example.telecare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Appointment {
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "medical_record_share", joinColumns = @JoinColumn(name = "appointment_id"), inverseJoinColumns = @JoinColumn(name = "medical_record_id"))
    public List<MedicalRecord> medicalRecords = new ArrayList<>();
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
    @Column(name = "schedule_id")
    private Integer scheduleId;
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
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "appointment")
    private AppointmentDetails appointmentDetails;
    @Basic
    @Column(name = "payment_status_id")
    private Integer paymentStatusId;
    @Basic
    @Column(name = "is_share_medical_record")
    private Byte isShareMedicalRecord;
    @Basic
    @Column(name = "is_add_medical_record")
    private byte isAddMedicalRecord;
    @Basic
    @Column(name = "is_send_fcm_upcoming")
    private Byte isSendFcmUpcoming;
    @Basic
    @Column(name = "is_send_fcm_ontime")
    private Byte isSendFcmOntime;




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Appointment that = (Appointment) o;

        if (id != that.id) return false;
        if (patientId != null ? !patientId.equals(that.patientId) : that.patientId != null) return false;
        if (doctorId != null ? !doctorId.equals(that.doctorId) : that.doctorId != null) return false;
        if (scheduleId != null ? !scheduleId.equals(that.scheduleId) : that.scheduleId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (patientId != null ? patientId.hashCode() : 0);
        result = 31 * result + (doctorId != null ? doctorId.hashCode() : 0);
        result = 31 * result + (scheduleId != null ? scheduleId.hashCode() : 0);
        return result;
    }

    public Byte getIsSendFcmUpcoming() {
        return isSendFcmUpcoming;
    }

    public void setIsSendFcmUpcoming(Byte isSendFcmUpcoming) {
        this.isSendFcmUpcoming = isSendFcmUpcoming;
    }

    public Byte getIsSendFcmOntime() {
        return isSendFcmOntime;
    }

    public void setIsSendFcmOntime(Byte isSendFcmOntime) {
        this.isSendFcmOntime = isSendFcmOntime;
    }
}
