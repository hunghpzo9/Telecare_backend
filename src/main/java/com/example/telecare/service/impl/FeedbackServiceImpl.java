package com.example.telecare.service.impl;

import com.example.telecare.dto.interfaces.FeedbackDTOInf;
import com.example.telecare.enums.FeedbackStatus;
import com.example.telecare.exception.NotFoundException;
import com.example.telecare.model.Feedback;
import com.example.telecare.repository.FeedbackRepository;
import com.example.telecare.service.FeedbackService;
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
        feedback.setIsHidden((byte)FeedbackStatus.SHOW_FEEDBACK.status);
        return feedbackRepository.save(feedback);
    }

    @Override
    public void updateFeedback(Feedback feedback, boolean isDelete) {
        Feedback currentFeedback = feedbackRepository.findFeedbackByAppointmentId(feedback.getAppointmentId());

        currentFeedback.setAppointmentId(feedback.getAppointmentId());
        currentFeedback.setComment(feedback.getComment());
        currentFeedback.setRating(feedback.getRating());
        if (isDelete)
            currentFeedback.setIsHidden((byte)FeedbackStatus.HIDDEN_FEEDBACK.status);
        else
            currentFeedback.setIsHidden((byte)FeedbackStatus.SHOW_FEEDBACK.status);

        feedbackRepository.save(currentFeedback);
    }

    @Override
    public Feedback findFeedBackByAppointmentId(int aid) {
        return feedbackRepository.findFeedbackByAppointmentId(aid);
    }

    @Override
    public void updateFeedbackStatusForAdmin(int id, Byte status) {
        Feedback feedback = feedbackRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Không tìm thấy phản hồi"));
        feedback.setIsHidden(status);
        feedbackRepository.save(feedback);
    }
}
