package com.example.telecare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Doctor {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "doctor_id")
    private int doctorId;
    @Basic
    @Column(name = "certificate")
    private String certificate;
    @Basic
    @Column(name = "job_place")
    private String jobPlace;
    @JsonIgnore
    @OneToOne
    @MapsId
    @JoinColumn(name = "doctor_id")
    private User user;
    @Basic
    @Column(name = "position")
    private String position;
    @Basic
    @Column(name = "identification_front")
    private String identificationFront;
    @Basic
    @Column(name = "identification_back")
    private String identificationBack;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Doctor doctor = (Doctor) o;

        if (doctorId != doctor.doctorId) return false;
        if (certificate != null ? !certificate.equals(doctor.certificate) : doctor.certificate != null) return false;
        if (jobPlace != null ? !jobPlace.equals(doctor.jobPlace) : doctor.jobPlace != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = doctorId;
        result = 31 * result + (certificate != null ? certificate.hashCode() : 0);
        result = 31 * result + (jobPlace != null ? jobPlace.hashCode() : 0);
        return result;
    }

}
