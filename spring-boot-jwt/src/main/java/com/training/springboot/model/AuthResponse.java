package com.training.springboot.model;

import lombok.Data;
import lombok.Getter;


public class AuthResponse {
    private String jwtToken;
    public AuthResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }
}
