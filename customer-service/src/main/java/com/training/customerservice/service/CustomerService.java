package com.training.customerservice.service;

import java.util.UUID;

import com.training.customerservice.dto.LoginRequest;
import com.training.customerservice.model.Customer;

public interface CustomerService {
	
	public Customer createCustomer(Customer customer);
	public Customer getCustomerById(UUID id);
	public String deleteCustomer(UUID id);
//	public Customer updateCustomer(Customer customer, UUID id);
//	public Customer getByEmail(LoginRequest request);

}
