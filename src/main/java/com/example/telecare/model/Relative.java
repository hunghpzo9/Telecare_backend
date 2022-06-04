package com.example.telecare.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Relative {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "full_name")
    private String fullName;
    @Basic
    @Column(name = "date_of_birth")
    private Timestamp dateOfBirth;
    @Basic
    @Column(name = "gender")
    private Byte gender;
    @Basic
    @Column(name = "relationship")
    private String relationship;
    @Basic
    @Column(name = "phone")
    private String phone;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "patient_id")
    private Integer patientId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Timestamp getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Timestamp dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Relative relative = (Relative) o;

        if (id != relative.id) return false;
        if (fullName != null ? !fullName.equals(relative.fullName) : relative.fullName != null) return false;
        if (dateOfBirth != null ? !dateOfBirth.equals(relative.dateOfBirth) : relative.dateOfBirth != null)
            return false;
        if (gender != null ? !gender.equals(relative.gender) : relative.gender != null) return false;
        if (relationship != null ? !relationship.equals(relative.relationship) : relative.relationship != null)
            return false;
        if (phone != null ? !phone.equals(relative.phone) : relative.phone != null) return false;
        if (email != null ? !email.equals(relative.email) : relative.email != null) return false;
        if (patientId != null ? !patientId.equals(relative.patientId) : relative.patientId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (relationship != null ? relationship.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (patientId != null ? patientId.hashCode() : 0);
        return result;
    }
}
