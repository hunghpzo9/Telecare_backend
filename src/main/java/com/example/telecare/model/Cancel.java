package com.example.telecare.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Cancel {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "cancel_reason_id")
    private Integer cancelReasonId;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Basic
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    @Basic
    @Column(name = "appointment_id")
    private Integer appointmentId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getCancelReasonId() {
        return cancelReasonId;
    }

    public void setCancelReasonId(Integer cancelReasonId) {
        this.cancelReasonId = cancelReasonId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cancel cancel = (Cancel) o;

        if (id != cancel.id) return false;
        if (cancelReasonId != null ? !cancelReasonId.equals(cancel.cancelReasonId) : cancel.cancelReasonId != null)
            return false;
        if (description != null ? !description.equals(cancel.description) : cancel.description != null) return false;
        if (createdAt != null ? !createdAt.equals(cancel.createdAt) : cancel.createdAt != null) return false;
        if (updatedAt != null ? !updatedAt.equals(cancel.updatedAt) : cancel.updatedAt != null) return false;
        if (appointmentId != null ? !appointmentId.equals(cancel.appointmentId) : cancel.appointmentId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (cancelReasonId != null ? cancelReasonId.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (appointmentId != null ? appointmentId.hashCode() : 0);
        return result;
    }
}
