package com.example.telecare.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class AuthenticationResponse {
    private String access_token;
    private String refresh_token;
    private int user_id;
    private String role;
    private byte is_active;
    private String ban_reason;
}
