package com.example.telecare.service.impl;

import com.example.telecare.dto.FeedbackDTOInf;
import com.example.telecare.model.Feedback;
import com.example.telecare.repository.FeedbackRepository;
import com.example.telecare.service.FeedbackService;
import com.example.telecare.utils.ProjectStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    FeedbackRepository feedbackRepository;

    @Override
    public List<FeedbackDTOInf> getListFeedBackByDoctor(int uid, int index) {
        return feedbackRepository.getListFeedBackByDoctor(uid, index);
    }

    @Override
    public Feedback saveNewFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    @Override
    public void updateFeedback(Feedback feedback, boolean isDelete) {
        Feedback currentFeedback = feedbackRepository.findFeedbackByAppointmentId(feedback.getAppointmentId());

        currentFeedback.setAppointmentId(feedback.getAppointmentId());
        currentFeedback.setComment(feedback.getComment());
        currentFeedback.setRating(feedback.getRating());
        if (isDelete)
            currentFeedback.setIsHidden((byte) ProjectStorage.HIDDEN_FEEDBACK);
        else
            currentFeedback.setIsHidden((byte) ProjectStorage.SHOW_FEEDBACK);

        feedbackRepository.save(currentFeedback);
    }

    @Override
    public Feedback findFeedBackByAppointmentId(int aid) {
        return feedbackRepository.findFeedbackByAppointmentId(aid);
    }
}
