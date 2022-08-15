package com.example.telecare.service;

import com.example.telecare.dto.interfaces.FeedbackDTOInf;
import com.example.telecare.model.Feedback;

import java.util.List;

public interface FeedbackService {
    List<FeedbackDTOInf> getListFeedBackByDoctor(int uid,int index);

    Feedback saveNewFeedback(Feedback feedback);

    void updateFeedback(Feedback feedback,boolean isDelete);

    Feedback findFeedBackByAppointmentId(int aid);

    void updateFeedbackStatusForAdmin(int id,Byte status);
}
