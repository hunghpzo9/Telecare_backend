package com.example.telecare.controller;

import com.example.telecare.dto.AuthenticationRequest;
import com.example.telecare.model.User;
import com.example.telecare.security.MyUserDetailsService;
import com.example.telecare.service.impl.AuthServiceImpl;
import com.example.telecare.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin(maxAge = 60 * 60 * 24 * 30)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    UserServiceImpl userService;

    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    MyUserDetailsService userDetailsService;

    @PostMapping("/register/patient")
    public User registerUserPatient(@RequestBody User user) {
        User registeredUser = userService.registerPatient(user);
        return registeredUser;
    }

    @PostMapping("/register/doctor")
    public User registerUserDoctor(@RequestBody User user) {
        User registeredUser = userService.registerDoctor(user);
        return registeredUser;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        return authService.createToken(authenticationRequest);
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authService.refreshToken(request,response);
    }

}
