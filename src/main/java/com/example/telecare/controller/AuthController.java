package com.example.telecare.controller;

import com.example.telecare.dto.AuthenticationRequest;
import com.example.telecare.model.User;
import com.example.telecare.model.UserRole;
import com.example.telecare.repository.UserRoleRepository;
import com.example.telecare.service.AuthService;
import com.example.telecare.service.impl.AuthServiceImpl;
import com.example.telecare.service.impl.UserServiceImpl;
import com.example.telecare.utils.ProjectStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 60 * 60 * 24 * 30)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    UserServiceImpl userService;

    @Autowired
    private AuthServiceImpl authService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/register/patient")
    public User registerUserPatient(@RequestBody User user) {
        User registeredUser = userService.registerPatient(user);
        logger.info("User ID: {}",user.getId());
        return registeredUser;
    }

    @PostMapping("/register/doctor")
    public User registerUserDoctor(@RequestBody User user) {
        User registeredUser = userService.registerDoctor(user);
        logger.info("User ID: {}",user.getId());
        return registeredUser;
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        return authService.createToken(authenticationRequest);
    }
}
