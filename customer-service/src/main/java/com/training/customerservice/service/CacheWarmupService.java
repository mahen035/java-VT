//package com.training.customerservice.service;
//
//import com.training.customerservice.model.Customer;
//import com.training.customerservice.repository.CustomerRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cache.annotation.CachePut;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class CacheWarmupService {
//
//    private final CustomerRepository customerRepository;
//
//    @CachePut(value = "customers", key = "#customer.id")
//    public void putCustomerInCache(Customer customer) {
//    }
//
//    public void warmUpCache() {
//        log.info("Warming up cache with all customers...");
//        List<Customer> customers = customerRepository.findAll();
//        customers.forEach(this::putCustomerInCache);
//        log.info("Cache warm-up complete with {} customers.", customers.size());
//    }
//}
