package com.training.customerservice.controller;

import com.netflix.discovery.converters.Auto;
import com.training.customerservice.service.EmailService;
import com.training.customerservice.service.FraudService;
import com.training.customerservice.service.InMemoryPipeService;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.training.customerservice.dto.LoginRequest;
import com.training.customerservice.intercomm.AccountClient;
import com.training.customerservice.model.Customer;
import com.training.customerservice.service.CustomerService;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
	 private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
	 @Autowired
	 private CustomerService service;

	 @Autowired
	 private InMemoryPipeService pipeService;
	 @Autowired
	 private AccountClient accountClient;


	@Autowired
	private EmailService emailService;

	@Autowired
	private FraudService fraudService;
	 
	 @Autowired
	 private PasswordEncoder passwordEncoder;

	 @PostMapping
	 public Customer create(@RequestBody Customer customer, @RequestParam Double balance, @RequestParam String accountType) {
		Customer newCustomer = service.addCustomer(customer);
		try(var scope = new StructuredTaskScope.ShutdownOnFailure()){
			var fraudCheck = scope.fork(()->
					fraudService.findFraud(newCustomer.getId()));
			var createAccount = scope.fork(()->
					accountClient.addAccount(newCustomer.getId(), balance, accountType));
			var sendEmail = scope.fork(()->
					emailService.sendEmail(newCustomer.getEmail()));
			scope.join();
			scope.throwIfFailed();
		}
		catch (InterruptedException | ExecutionException e){
			e.printStackTrace();
		}
		//accountClient.addAccount(newCustomer.getId(), balance, accountType);
		//fraudClient.find(newCustomer.getId())
		 // emailClient.sendEmail(newCustomer.getEmail())
		return newCustomer;
	  }


	@GetMapping("/hello")
	public String sayHello() {
		return "Hello from Customer Service - " + UUID.randomUUID();
	}

     @GetMapping("/read-io")
	 public ResponseEntity<String> readFileUsingIO() throws IOException {
		 long start = System.currentTimeMillis();
		 ClassPathResource resource = new ClassPathResource("customers.txt");
		 Path path = resource.getFile().toPath();
		 try(BufferedReader reader = Files.newBufferedReader(path)){
			 while(reader.readLine() != null){
				 log.info("IO file read in progress..");
			 }
		 }
		 long duration = System.currentTimeMillis() - start;
		 return ResponseEntity.ok("IO Read complete in: "+duration+"ms");
	 }

	 @GetMapping("/relayMsg")
	 public ResponseEntity<String> relay(@RequestParam String message) throws IOException {
		 pipeService.startCommunication(message);
		 return ResponseEntity.ok("Inter thread communication..");
	 }

	@GetMapping("/read-nio")
	public ResponseEntity<String> readFileUsingNIO() throws IOException {
		long start = System.currentTimeMillis();
		ClassPathResource resource = new ClassPathResource("customers.txt");
		Path path = resource.getFile().toPath();
		try(FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ)){
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			while(fileChannel.read(buffer) > 0){
				buffer.flip();
				while(buffer.hasRemaining()){
					buffer.get();
				}
				buffer.clear();
			}
		}
		long duration = System.currentTimeMillis() - start;
		return ResponseEntity.ok("NIO Read complete in: "+duration+"ms");
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
