package com.example.telecare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Patient {
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "favorite_doctor", joinColumns = @JoinColumn(name = "patient_id"), inverseJoinColumns = @JoinColumn(name = "doctor_id"))
    public Set<Doctor> favoriteDoctor = new HashSet<>();
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "patient_id")
    private int patientId;
    @Basic
    @Column(name = "blood_type")
    private String bloodType;
    @Basic
    @Column(name = "height")
    private Double height;
    @Basic
    @Column(name = "weight")
    private Double weight;
    @Basic
    @Column(name = "job")
    private String job;
    @Basic
    @Column(name = "job_place")
    private String jobPlace;
    @Basic
    @Column(name = "ethnic_id")
    private Integer ethnicId;
    @JsonIgnore
    @OneToOne
    @MapsId
    @JoinColumn(name = "patient_id")
    private User user;
    @Basic
    @Column(name = "insurance")
    private String insurance;

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getJobPlace() {
        return jobPlace;
    }

    public void setJobPlace(String jobPlace) {
        this.jobPlace = jobPlace;
    }

    public Integer getEthnicId() {
        return ethnicId;
    }

    public void setEthnicId(Integer ethnicId) {
        this.ethnicId = ethnicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Patient patient = (Patient) o;

        if (patientId != patient.patientId) return false;
        if (bloodType != null ? !bloodType.equals(patient.bloodType) : patient.bloodType != null) return false;
        if (height != null ? !height.equals(patient.height) : patient.height != null) return false;
        if (weight != null ? !weight.equals(patient.weight) : patient.weight != null) return false;
        if (job != null ? !job.equals(patient.job) : patient.job != null) return false;
        if (jobPlace != null ? !jobPlace.equals(patient.jobPlace) : patient.jobPlace != null) return false;
        if (ethnicId != null ? !ethnicId.equals(patient.ethnicId) : patient.ethnicId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = patientId;
        result = 31 * result + (bloodType != null ? bloodType.hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (job != null ? job.hashCode() : 0);
        result = 31 * result + (jobPlace != null ? jobPlace.hashCode() : 0);
        result = 31 * result + (ethnicId != null ? ethnicId.hashCode() : 0);
        return result;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }
}
