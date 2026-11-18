package com.example.ApiRest_Spa.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
