package com.example.telecare.model;

import javax.persistence.*;

@Entity
@Table(name = "doctor_achievement", schema = "telecare", catalog = "")
public class DoctorAchievement {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "achievement")
    private String achievement;
    @Basic
    @Column(name = "doctor_id")
    private Integer doctorId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DoctorAchievement that = (DoctorAchievement) o;

        if (id != that.id) return false;
        if (achievement != null ? !achievement.equals(that.achievement) : that.achievement != null) return false;
        if (doctorId != null ? !doctorId.equals(that.doctorId) : that.doctorId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (achievement != null ? achievement.hashCode() : 0);
        result = 31 * result + (doctorId != null ? doctorId.hashCode() : 0);
        return result;
    }
}
