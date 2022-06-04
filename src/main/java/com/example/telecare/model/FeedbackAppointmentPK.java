package com.example.telecare.model;

import javax.persistence.*;
import java.io.Serializable;

public class FeedbackAppointmentPK implements Serializable {
    @Column(name = "feedback_id")
    @Basic
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer feedbackId;
    @Column(name = "apointment_id")
    @Basic
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer apointmentId;

    public Integer getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Integer feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Integer getApointmentId() {
        return apointmentId;
    }

    public void setApointmentId(Integer apointmentId) {
        this.apointmentId = apointmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeedbackAppointmentPK that = (FeedbackAppointmentPK) o;

        if (feedbackId != null ? !feedbackId.equals(that.feedbackId) : that.feedbackId != null) return false;
        if (apointmentId != null ? !apointmentId.equals(that.apointmentId) : that.apointmentId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = feedbackId != null ? feedbackId.hashCode() : 0;
        result = 31 * result + (apointmentId != null ? apointmentId.hashCode() : 0);
        return result;
    }
}
