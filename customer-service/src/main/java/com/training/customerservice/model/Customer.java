package com.training.customerservice.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Customer implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private UUID id;
    
    @Column(nullable=false)
    private String name;
    
    @Column(nullable=false)
    private String email;
    
    @Column(nullable=false)
    private String password;
    
    @Size(min=8, max=8)
    private String phone;
    private String address;
}
