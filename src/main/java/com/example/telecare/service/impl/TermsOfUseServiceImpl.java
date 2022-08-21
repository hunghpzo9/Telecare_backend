package com.example.telecare.service.impl;

import com.example.telecare.repository.TermsOfUseRepository;
import com.example.telecare.service.TermsOfUseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TermsOfUseServiceImpl implements TermsOfUseService {
    @Autowired
    TermsOfUseRepository termsOfUseRepository;
    @Override
    public String getTermsOfUseUrl() {
        return termsOfUseRepository.getTermsOfUseUrl();
    }
}
