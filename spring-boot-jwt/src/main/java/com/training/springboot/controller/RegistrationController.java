package com.training.springboot.controller;

import com.training.springboot.model.User;
import com.training.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserRepository repository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return new ResponseEntity<>(repository.save(user), HttpStatus.CREATED);
    }
}
