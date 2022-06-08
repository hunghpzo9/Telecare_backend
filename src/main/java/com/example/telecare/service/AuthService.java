package com.example.telecare.service;

import com.example.telecare.dto.AuthenticationRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {
    ResponseEntity<?> createToken(AuthenticationRequest authenticationRequest) throws Exception;

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception;

    ResponseEntity<?> changeOldPassword(String id, String oldPassword,String newPassword);
}
