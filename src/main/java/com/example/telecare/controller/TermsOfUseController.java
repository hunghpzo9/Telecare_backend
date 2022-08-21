package com.example.telecare.controller;

import com.example.telecare.dto.ResponseOkMessage;
import com.example.telecare.model.Specialty;
import com.example.telecare.service.impl.TermsOfUseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@CrossOrigin(maxAge = 60 * 60 * 24 * 30)
@RestController
@RequestMapping("/api/v1/terms")
public class TermsOfUseController {
    @Autowired
    TermsOfUseServiceImpl termsOfUseService;

    @Cacheable(value = "termsOfUse")
    @GetMapping(value = "")
    public ResponseEntity<?> getTermsOfUse() {
        return ResponseEntity.ok(new ResponseOkMessage( termsOfUseService.getTermsOfUseUrl(),new Date()));


    }
}
