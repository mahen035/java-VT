package com.training.customerservice.controller;
import brave.Tracer;
import com.training.customerservice.dto.CustomerRequest;
import com.training.customerservice.service.CustomerServiceImpl;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
//
//	 @Autowired
//	 private PasswordEncoder passwordEncoder;
	@Autowired
	private Tracer tracer;
	private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

//	 @PostMapping
//	 public Customer create(@RequestBody Customer customer, @RequestParam Double balance, @RequestParam String accountType) {
//		 Customer newCustomer = service.addCustomer(customer);
//		accountClient.addAccount(newCustomer.getId(), balance, accountType);
//		return newCustomer;
//	  }


	@PostMapping
	public Customer create(@RequestBody Customer customer){
		return service.createCustomer(customer);
	}

	@DeleteMapping("/{id}")
	public String deleteUser(@PathVariable UUID id){
		return service.deleteCustomer(id);
	}

	@GetMapping("/{id}")
	public Customer getById(@PathVariable UUID id){
		return service.getCustomerById(id);
	}

	@GetMapping("/hello")
	public String sayHello() {
		return "Hello from Customer Service - " + UUID.randomUUID();
	}
	 
//	 @PostMapping("/login")
//	 public ResponseEntity<?> login(@RequestBody LoginRequest request) {
//		Customer customer =  service.getByEmail(request);
//		if(customer != null) {
//			if (passwordEncoder.matches(request.getPassword(), customer.getPassword())) {
//	            return ResponseEntity.ok(customer);
//	        } else {
//	            return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).body("Invalid password");
//	        }
//		}
//		else {
//	        return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body("Customer not found");
//	    }
//	  }
	 
	 
	 
}
