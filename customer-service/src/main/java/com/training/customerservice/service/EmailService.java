package com.training.customerservice.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    public String sendEmail(String email){
        System.out.println("Email successfully sent");
        return "Email sent";
    }
}
