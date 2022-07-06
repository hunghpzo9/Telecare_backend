package com.example.telecare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Entity
public class Doctor {
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "doctor_specialty", joinColumns = @JoinColumn(name = "doctor_id"), inverseJoinColumns = @JoinColumn(name = "specialty_id"))
    public List<Specialty> specialties = new ArrayList<>();
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
    @Basic
    @Column(name = "signature")
    private String signature;
    @Basic
    @Column(name = "expire_date_certificate")
    private Date expireDateCertificate;
    public int getDoctorId() {
        return doctorId;
    }

    public Date getExpireDateCertificate() {
        return expireDateCertificate;
    }

    public void setExpireDateCertificate(Date expireDateCertificate) {
        this.expireDateCertificate = expireDateCertificate;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getJobPlace() {
        return jobPlace;
    }

    public void setJobPlace(String jobPlace) {
        this.jobPlace = jobPlace;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getIdentificationFront() {
        return identificationFront;
    }

    public void setIdentificationFront(String identificationFront) {
        this.identificationFront = identificationFront;
    }

    public String getIdentificationBack() {
        return identificationBack;
    }

    public void setIdentificationBack(String identificationBack) {
        this.identificationBack = identificationBack;
    }

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

    public void addSpecialty(Specialty specialty) {
        this.specialties.add(specialty);
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
