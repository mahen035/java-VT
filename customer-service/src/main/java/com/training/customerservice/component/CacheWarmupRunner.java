//package com.training.customerservice.component;
//
//import com.training.customerservice.model.Customer;
//import com.training.customerservice.repository.CustomerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
////import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class CacheWarmupRunner implements ApplicationRunner {
//
//    @Autowired
//    private CustomerRepository repository;
//
////    @Autowired
////    private RedisTemplate<String, Object> redisTemplate;
//
////    @Override
////    public void run(ApplicationArguments args) {
////        List<Customer> customers = repository.findAll();
////        for (Customer customer : customers) {
////            String key = "customers::" + customer.getId();
////            redisTemplate.opsForValue().set(key, customer);
////        }
////    }
//}
//
