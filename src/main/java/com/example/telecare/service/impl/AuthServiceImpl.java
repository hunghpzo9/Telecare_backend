package com.example.telecare.service.impl;

import com.example.telecare.dto.AuthenticationRequest;
import com.example.telecare.dto.AuthenticationResponse;
import com.example.telecare.model.Role;
import com.example.telecare.model.User;
import com.example.telecare.repository.UserRepository;
import com.example.telecare.security.MyUserDetailsService;
import com.example.telecare.service.AuthService;
import com.example.telecare.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

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
            throw new Exception("Incorrect username or password", e);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getPhone());
        final String access_token = jwtTokenUtil.generateAccessToken(userDetails);
        final String refresh_token = jwtTokenUtil.generateRefreshToken(userDetails);
        User user = userRepository.findUserByPhone(authenticationRequest.getPhone());
        Role user_role = user.getRoles().stream().reduce((first, second) -> first).orElse(null);

        return ResponseEntity.ok(new AuthenticationResponse(access_token, refresh_token, user.getId(),user_role.getName()));
    }
}
