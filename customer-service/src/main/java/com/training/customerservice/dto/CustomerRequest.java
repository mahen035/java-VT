package com.training.customerservice.dto;

import java.io.Serial;
import java.io.Serializable;

public record CustomerRequest(String name, String email, String phone, String address) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
