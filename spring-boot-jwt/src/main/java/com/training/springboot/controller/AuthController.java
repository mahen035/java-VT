package com.training.springboot.controller;
import com.training.springboot.component.TokenBlacklist;
import com.training.springboot.model.AuthRequest;
import com.training.springboot.model.AuthResponse;
import com.training.springboot.model.User;
import com.training.springboot.service.CustomUserDetailService;
import com.training.springboot.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import java.security.Key;
import java.util.Objects;
@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authManager;
//    @Autowired
//    private TokenBlacklist tokenBlacklist;
    @Autowired
    CustomUserDetailService userDetailService;
    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping("/login")
    public String authenticate(@RequestBody AuthRequest authRequest) {
       Authentication authentication =  authManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.username(), authRequest.password()));

       if(authentication.isAuthenticated()){
           return jwtUtil.generateToken(userDetailService.loadUserByUsername(authRequest.username()));
       }
       else{
           throw new UsernameNotFoundException("Invalid Credential");
       }
    }
    @PostMapping("/app-logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        try {
            String token = jwtUtil.resolveToken(request);
            if (!Objects.isNull(token)) {
                jwtUtil.revokeToken(token);
                System.out.println("User logged out successfully.");
                return ResponseEntity.ok("User logged out successfully.");
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception logoutException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/home")
    public String hello() {
        return "Hello, Welcome to home page!";
    }

}
