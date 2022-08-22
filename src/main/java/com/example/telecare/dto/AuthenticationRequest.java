package com.example.telecare.dto;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AuthenticationRequest {
    private String phone;
    private String password;
    private String fcmToken;
}
