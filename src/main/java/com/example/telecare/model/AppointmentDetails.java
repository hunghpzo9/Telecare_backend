package com.example.telecare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "status_id")
    private Integer statusId;
    @Basic
    @Column(name = "time")
    private Date time;
    @Basic
    @Column(name = "description")
    private String description;
    @JsonIgnore
    @OneToOne
    @MapsId
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
    @Basic
    @Column(name = "refuse_fill_reason")
    private String refuseFillReason;
    @Basic
    @Column(name = "amount")
    private Integer amount;

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppointmentDetails that = (AppointmentDetails) o;

        return true;
    }

    @Override
    public int hashCode() {
        int result = appointmentId;

        return result;
    }

    public String getRefuseFillReason() {
        return refuseFillReason;
    }

    public void setRefuseFillReason(String refuseFillReason) {
        this.refuseFillReason = refuseFillReason;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
