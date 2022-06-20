package com.example.telecare.controller;
import com.example.telecare.dto.FeedbackDTOInf;
import com.example.telecare.model.Feedback;
import com.example.telecare.service.impl.FeedbackServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 60 * 60 * 24 * 30)
@RestController
@RequestMapping("/api/v1/feedback")
public class FeedbackController {
    @Autowired
    FeedbackServiceImpl feedbackService;

    @GetMapping(value = "/doctorId={id}")
    @Cacheable(value = "feedback")
    public List<FeedbackDTOInf> getListFeedBackByDoctor(@PathVariable int id, @Param("index") int index) {
        return feedbackService.getListFeedBackByDoctor(id,index);
    }
    @PostMapping(value = "/save")
    public ResponseEntity<?> postNewFeedback(@RequestBody Feedback feedback) {
         feedbackService.saveNewFeedback(feedback);
        return ResponseEntity.ok(feedback);
    }
    @PutMapping(value = "/update")
    public ResponseEntity<?> updateFeedback(@RequestBody Feedback feedback,@Param("isDelete") boolean isDelete) {
        feedbackService.updateFeedback(feedback,isDelete);
        return ResponseEntity.ok(feedback);
    }
    @GetMapping(value = "/appointmentId={id}")
    public Feedback getFeedbackByAppointmentId(@PathVariable int id) {
        return feedbackService.findFeedBackByAppointmentId(id);
    }
}
