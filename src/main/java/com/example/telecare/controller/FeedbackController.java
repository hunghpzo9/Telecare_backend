package com.example.telecare.controller;
import com.example.telecare.dto.FeedbackDTOInf;
import com.example.telecare.service.impl.FeedbackServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 60 * 60 * 24 * 30)
@RestController
@RequestMapping("/api/v1/feedback")
public class FeedbackController {
    @Autowired
    FeedbackServiceImpl feedbackService;
    @GetMapping(value = "/doctorId={id}")
    public List<FeedbackDTOInf> getListFeedBackByDoctor(@PathVariable int id) {
        return feedbackService.getListFeedBackByDoctor(id);
    }
}
