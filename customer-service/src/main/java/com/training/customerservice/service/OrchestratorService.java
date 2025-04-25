//package com.training.customerservice.service;
//
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//public class OrchestratorService {
//    private final RestTemplate restTemplate = new RestTemplate();
//
//    public String performTransfer(String from, String to, double amount) {
//        try {
//            // Step 1: Debit
//            restTemplate.postForObject("http://localhost:8081/customer/debit",
//                    new TransferDTO(from, amount), String.class);
//            // Step 2: Log Transaction
//            restTemplate.postForObject("http://localhost:8082/transaction/log",
//                    new TransferDTO(from, amount), String.class);
//            // Step 3: Send Notification
//            restTemplate.postForObject("http://localhost:8083/notify",
//                    new TransferDTO(to, amount), String.class);
//            return "Transfer Successful";
//        } catch (Exception e) {
//            System.out.println("Error occurred: " + e.getMessage());
//            // Compensation logic
//            restTemplate.postForObject("http://localhost:8081/customer/refund",
//                    new TransferDTO(from, amount), String.class);
//            restTemplate.postForObject("http://localhost:8082/transaction/deleteLog",
//                    new TransferDTO(from, amount), String.class);
//            return "Transfer Rolled Back Due to Error";
//        }
//    }
//}
