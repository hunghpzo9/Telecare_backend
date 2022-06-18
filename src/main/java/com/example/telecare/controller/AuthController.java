package com.example.telecare.controller;

import com.example.telecare.dto.AuthenticationRequest;
import com.example.telecare.dto.DoctorDTO;
import com.example.telecare.model.User;
import com.example.telecare.security.MyUserDetailsService;
import com.example.telecare.service.impl.AuthServiceImpl;
import com.example.telecare.service.impl.UserServiceImpl;
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

    @PostMapping("/register/patient")
    public User registerUserPatient(@RequestBody User user) {
        User registeredUser = userService.registerPatient(user);
        return registeredUser;
    }

    @PostMapping("/register/admin")
    public User registerUserAdmin(@RequestBody User user) {
        User registeredAdmin = userService.registerAdmin(user);
        return registeredAdmin;
    }

    @PostMapping("/register/doctor")
    public ResponseEntity<?> registerUserDoctor(@RequestBody DoctorDTO doctorDTO) {
        userService.registerDoctor(doctorDTO);
        return ResponseEntity.ok(doctorDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        return authService.login(authenticationRequest);
    }
    @PostMapping("/loginForAdmin")
    public ResponseEntity<?> loginForAdmin(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        return authService.loginForAdmin(authenticationRequest);
    }

    @PostMapping("/checkEmailExisted/{email}")
    public ResponseEntity<?> checkEmailExisted(@PathVariable String email) {
        return authService.checkEmailExisted(email);
    }

    @PostMapping("/checkPhoneExisted/{phone}")
    public ResponseEntity<?> checkPhoneExisted(@PathVariable String phone) {
        return authService.checkPhoneExisted(phone);
    }

    @PutMapping("/changePassword/{id}/{password}/{newPassword}")
    public ResponseEntity<?> changeOldPassword(@PathVariable String id, @PathVariable String password, @PathVariable String newPassword) {
        return authService.changeOldPassword(id, password, newPassword);
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authService.refreshToken(request, response);
    }

}
