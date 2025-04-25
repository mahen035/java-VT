package com.training.springboot.exception;
public class MyResourceNotFoundException extends RuntimeException{
     public MyResourceNotFoundException(String msg){
        super(msg);
    }
}
