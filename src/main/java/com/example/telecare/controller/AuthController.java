package com.example.telecare.controller;

import com.example.telecare.dto.AuthenticationRequest;
import com.example.telecare.dto.DoctorDTO;
import com.example.telecare.dto.TwilioRequestDTO;
import com.example.telecare.model.User;
import com.example.telecare.service.impl.AuthServiceImpl;
import com.example.telecare.service.impl.TwilioServiceImpl;
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
    private UserServiceImpl userService;

    @Autowired
    private TwilioServiceImpl twilioService;

    @Autowired
    private AuthServiceImpl authService;

    @PostMapping("/register/patient")
    public User registerUserPatient(@RequestBody User user) {
        User registeredUser = userService.registerPatient(user);
        return registeredUser;
    }

    @PostMapping("/register/admin")
    public User registerUserAdmin(@RequestBody User user,@RequestParam String role) {
        User registeredAdmin = userService.registerAdmin(user,role);
        return registeredAdmin;
    }

    @PostMapping("/register/doctor")
    public ResponseEntity<?> registerUserDoctor(@RequestBody DoctorDTO doctorDTO) {
        userService.registerDoctor(doctorDTO);
        return ResponseEntity.ok(doctorDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return authService.login(authenticationRequest);
    }
    @PostMapping("/loginForAdmin")
    public ResponseEntity<?> loginForAdmin(@RequestBody AuthenticationRequest authenticationRequest) {
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

    @PutMapping("/changePassword")
    public ResponseEntity<?> changeOldPassword(@RequestParam("id") String id,
                                               @RequestParam("password") String password,
                                               @RequestParam("newPassword") String newPassword) {
        return authService.changeOldPassword(id, password, newPassword);
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authService.refreshToken(request, response);
    }
    @PostMapping("/otp/sendOtp")
    public ResponseEntity<?> sendOtp(@RequestBody TwilioRequestDTO twilioRequestDTO) {
        return twilioService.sendOtp(twilioRequestDTO);
    }
    @PostMapping("/otp/validateOtp")
    public ResponseEntity<?> validateOtp(@RequestBody TwilioRequestDTO twilioRequestDTO) {
        return twilioService.validateOtp(twilioRequestDTO.getOtp(),twilioRequestDTO.getPhoneNumber());
    }
    @PutMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestParam("phone") String phone,
                                            @RequestParam("newPassword") String newPassword) {
        return authService.forgotPassword(phone, newPassword);
    }

}
