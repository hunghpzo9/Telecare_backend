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


}
