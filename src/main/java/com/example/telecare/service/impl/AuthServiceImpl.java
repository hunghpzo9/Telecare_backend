package com.example.telecare.service.impl;

import com.example.telecare.dto.AuthenticationRequest;
import com.example.telecare.dto.AuthenticationResponse;
import com.example.telecare.dto.ResponseOkMessage;
import com.example.telecare.exception.BadRequestException;
import com.example.telecare.exception.ForbiddenException;
import com.example.telecare.model.Role;
import com.example.telecare.model.User;
import com.example.telecare.repository.UserRepository;
import com.example.telecare.security.MyUserDetailsService;
import com.example.telecare.security.PasswordHashService;
import com.example.telecare.service.AuthService;
import com.example.telecare.utils.JwtTokenUtil;
import com.example.telecare.utils.ProjectStorage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    PasswordHashService passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    MyUserDetailsService userDetailsService;
    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity<?> createToken(AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getPhone(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new ForbiddenException("Số điện thoại hoặc mật khẩu không đúng. Vui lòng kiểm tra và thử lại.");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getPhone());
        final String access_token = jwtTokenUtil.generateAccessToken(userDetails);
        final String refresh_token = jwtTokenUtil.generateRefreshToken(userDetails);
        User user = userRepository.findUserByPhone(authenticationRequest.getPhone());
        Role user_role = user.getRoles().stream().reduce((first, second) -> first).orElse(null);
        return ResponseEntity.ok(new AuthenticationResponse(access_token, refresh_token, user.getId(),user_role.getName()));
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final String authorizationHeader = request.getHeader(ProjectStorage.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith(ProjectStorage.BEARER)) {
            try {
                String refresh_token = authorizationHeader.substring(ProjectStorage.BEARER.length());
                String phone = jwtTokenUtil.getUsernameFromToken(refresh_token);
                final UserDetails userDetails = userDetailsService.loadUserByUsername(phone);
                String access_token = jwtTokenUtil.generateAccessToken(userDetails);
                Map<String, String> tokens = new HashMap<>();
                tokens.put(ProjectStorage.ACCESS_TOKEN, access_token);
                tokens.put(ProjectStorage.REFRESH_TOKEN, refresh_token);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

    @Override
    public ResponseEntity<?> changeOldPassword(String id, String oldPassword,String newPassword) {
        User user = userRepository.findUserById(id);
        if(!oldPassword.equals(decodePassword(user))){
            throw new ForbiddenException("Mật khẩu ban đầu không đúng");
        }else{
            encodePassword(user,newPassword);
            userRepository.save(user);
            return ResponseEntity.ok(new ResponseOkMessage("Mật khẩu đã được thay đổi",new Date()));
        }
    }

    @Override
    public ResponseEntity<?> checkEmailExisted(String email) {
        User duplicateUserByEmail = userRepository.findUserByEmail(email);
        if (duplicateUserByEmail != null) {
            throw new BadRequestException("Email đã tồn tại");
        }
        return ResponseEntity.ok(new ResponseOkMessage("Email có thể dùng được",new Date()));
    }

    @Override
    public ResponseEntity<?> checkPhoneExisted(String phone) {
        User duplicatUserByPhone = userRepository.findUserByPhone(phone);
        if (duplicatUserByPhone != null) {
            throw new BadRequestException("Số điện thoại đã tồn tại");
        }
        return ResponseEntity.ok(new ResponseOkMessage("Số điện thoại có thể dùng được",new Date()));
    }

    private void encodePassword(User user,String newPassword) {
        UUID randomUUID = UUID.randomUUID();
        String salt = randomUUID.toString().replaceAll("-", "").substring(0, 7);
        String encodePass = passwordEncoder.encodePasswordAlgorithm(salt, newPassword);
        user.setPassword(encodePass);
        user.setPasswordSalt(salt);
    }
    private String decodePassword(User user) {
        String decodePass = passwordEncoder.decodePasswordAlgorithm(user.getPassword());
        System.out.println("Current:" +decodePass.substring(7));
        return decodePass.substring(7);
    }
}
