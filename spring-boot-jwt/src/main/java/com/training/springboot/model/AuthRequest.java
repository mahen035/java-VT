package com.training.springboot.model;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
public record AuthRequest(String username, String password) {

}
