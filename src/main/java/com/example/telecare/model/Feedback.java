package com.example.telecare.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Feedback {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "comment")
    private String comment;
    @Basic
    @Column(name = "rating")
    private Double rating;
    @Basic
    @Column(name = "is_hidden")
    private Byte isHidden;
    @Basic
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Basic
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Byte getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Byte isHidden) {
        this.isHidden = isHidden;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Feedback feedback = (Feedback) o;

        if (id != feedback.id) return false;
        if (comment != null ? !comment.equals(feedback.comment) : feedback.comment != null) return false;
        if (rating != null ? !rating.equals(feedback.rating) : feedback.rating != null) return false;
        if (isHidden != null ? !isHidden.equals(feedback.isHidden) : feedback.isHidden != null) return false;
        if (createdAt != null ? !createdAt.equals(feedback.createdAt) : feedback.createdAt != null) return false;
        if (updatedAt != null ? !updatedAt.equals(feedback.updatedAt) : feedback.updatedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (isHidden != null ? isHidden.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }
}
