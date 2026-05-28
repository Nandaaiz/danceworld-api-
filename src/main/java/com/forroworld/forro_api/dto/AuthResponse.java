package com.forroworld.forro_api.dto;

public class AuthResponse {
    private String token;
    private String email;
    private String userType;

    public AuthResponse(String token, String email, String userType) {
        this.token = token;
        this.email = email;
        this.userType = userType;
    }

    public String getToken() { return token; }
    public String getEmail() { return email; }
    public String getUserType() { return userType; }
}