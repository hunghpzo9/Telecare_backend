package com.example.telecare.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AuthenticationResponse {
    private String access_token;
    private String refresh_token;
    private int user_id;
    private String role;
}
