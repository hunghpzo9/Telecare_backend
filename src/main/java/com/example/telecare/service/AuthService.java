package com.example.telecare.service;

import com.example.telecare.dto.AuthenticationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthService {
    ResponseEntity<?> createToken(AuthenticationRequest authenticationRequest) throws Exception;
}
