package com.example.telecare.service;

import com.example.telecare.dto.FeedbackDTOInf;

import java.util.List;

public interface FeedbackService {
    List<FeedbackDTOInf> getListFeedBackByDoctor(int uid);
}
