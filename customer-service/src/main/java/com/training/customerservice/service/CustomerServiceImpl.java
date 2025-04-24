package com.training.customerservice.service;

import java.util.UUID;

import com.netflix.discovery.converters.Auto;
import com.training.customerservice.component.CacheEvictionManager;
import com.training.customerservice.exception.InvalidCustomerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

//import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.training.customerservice.model.Customer;
import com.training.customerservice.repository.CustomerRepository;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	private CustomerRepository repository;

	@Autowired
	private RedisHealthMonitorService redisHealthMonitor;

	@Autowired
	private RedisTemplate<String,Object> redisTemplate;

	@Autowired
	private CacheEvictionManager cacheEvictionManager;


//	@Autowired
//    private PasswordEncoder passwordEncoder;

	@Cacheable(value = "customers", key = "#id", condition = "@redisHealthMonitorService.isRedisAvailable()")
	public Customer getCustomerById(UUID id) {
		simulateSlowService(); // simulate DB delay
		return repository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
	}

	private void simulateSlowService() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public String deleteCustomer(UUID id) {
		repository.deleteById(id);
		if(redisHealthMonitor.isRedisAvailable()){
			log.info("DELETING FROM REDIS SERVER...");
			redisTemplate.delete("customers::"+id);
//			boolean isDeleted = redisTemplate.delete("customers::00ec802c-a5f2-41a0-9806-14c393d5d826");
//			System.out.println("Record Deleted:: "+isDeleted);
		}
		else{
			cacheEvictionManager.addFailedEvicts(id);
			log.info("Redis is down....");
		}
		return "User record for id: " + id + " deleted successfully ";
	}
	@CachePut(value = "customers", key = "#result.id")
	public Customer createCustomer(Customer customer) {
		return repository.save(customer);
	}

//	@Override
//	public Customer addCustomer(Customer customer) {
//		//String encryptedPassword = passwordEncoder.encode(customer.getPassword());
//	    //customer.setPassword(encryptedPassword);
//		if(customer.getName().equals("")) {
//			throw new InvalidCustomerException("Customer information missing");
//		}
//		return repository.save(customer);
//	}
//
//	@Override
//	public Customer getCustomerById(UUID id) {
//		return repository.findById(id).orElse(null);
//	}

//	@Override
//	public Customer updateCustomer(Customer customer, UUID id) {
//		 Customer existingCustomer = getCustomerById(id);
//	        if (existingCustomer != null) {
//	        	existingCustomer.setName(customer.getName());
//	        	existingCustomer.setEmail(customer.getEmail());
//	        	existingCustomer.setPhone(customer.getPhone());
//	        	existingCustomer.setAddress(customer.getAddress());
//	            return repository.save(existingCustomer);
//	        }
//	        return null;
//	}

//	@Override
//	public Customer getByEmail(LoginRequest request) {
//		Optional<Customer> customerOpt = repository.findByEmail(request.getEmail());
//
//	    if (customerOpt.isPresent()) {
//	        Customer customer = customerOpt.get();
//	        if (passwordEncoder.matches(request.getPassword(), customer.getPassword())) {
//	            return customer;
//	        } else {
//	            return null;
//	        }
//	    } else {
//	        return null;
//	    }
//	}

}
