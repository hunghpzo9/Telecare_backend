package com.example.telecare.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private Date dateOfBirth;
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
    @Basic
    @Column(name = "image_url")
    private String imageUrl;
    @Basic
    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;
    @Basic
    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;
    @Basic
    @Column(name = "ethnic_id")
    private Integer ethnicId;


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

    public Integer getEthnicId() {
        return ethnicId;
    }

    public void setEthnicId(Integer ethnicId) {
        this.ethnicId = ethnicId;
    }
}
