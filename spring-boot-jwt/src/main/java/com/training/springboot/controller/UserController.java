package com.training.springboot.controller;

import com.training.springboot.model.User;
import com.training.springboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository repository;
    @Autowired
    PasswordEncoder passwordEncoder;

//    @PostMapping
//    public ResponseEntity<User> addUser(@RequestBody User user){
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        return new ResponseEntity<>(repository.save(user), HttpStatus.CREATED);
//    }
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){

        return new ResponseEntity<>(repository.findAll(),HttpStatus.OK);
    }
    @GetMapping("/home")
    public String userHome(){
        return "Welcome to User Home!";
    }
}
