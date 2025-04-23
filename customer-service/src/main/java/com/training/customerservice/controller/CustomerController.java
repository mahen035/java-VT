package com.training.customerservice.controller;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.training.customerservice.dto.LoginRequest;
import com.training.customerservice.intercomm.AccountClient;
import com.training.customerservice.model.Customer;
import com.training.customerservice.service.CustomerService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

	 @Autowired
	 private CustomerService service;
	 
	 @Autowired
	 private AccountClient accountClient;
	 
	 @Autowired
	 private PasswordEncoder passwordEncoder;

	 @PostMapping
	 public Customer create(@RequestBody Customer customer, @RequestParam Double balance, @RequestParam String accountType) {
		Customer newCustomer = service.addCustomer(customer); 
		accountClient.addAccount(newCustomer.getId(), balance, accountType);
		return newCustomer;
	  }
	@GetMapping("/hello")
	public String sayHello() {
		return "Hello from Customer Service - " + UUID.randomUUID();
	}
	 
	 @PostMapping("/login")
	 public ResponseEntity<?> login(@RequestBody LoginRequest request) {
		Customer customer =  service.getByEmail(request);
		if(customer != null) {
			if (passwordEncoder.matches(request.getPassword(), customer.getPassword())) {
	            return ResponseEntity.ok(customer);
	        } else {
	            return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).body("Invalid password");
	        }
		}
		else {
	        return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body("Customer not found");
	    }
	  }
	 
	 
	 
}
