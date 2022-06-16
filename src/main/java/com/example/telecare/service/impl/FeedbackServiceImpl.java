package com.example.telecare.service.impl;

import com.example.telecare.dto.FeedbackDTOInf;
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
    public List<FeedbackDTOInf> getListFeedBackByDoctor(int uid) {
        return feedbackRepository.getListFeedBackByDoctor(uid);
    }
}
