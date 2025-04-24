package com.training.customerservice.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FraudService {

    public String findFraud(UUID custId){
        System.out.println("Fraud Service in action");
        return "Fraud Detected for: "+custId;
    }

}
