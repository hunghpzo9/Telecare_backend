package com.example.telecare.exception;

public class UnauthorizedException extends RuntimeException{

    private  static final long  serialVersionUID =1L;

    public UnauthorizedException(String msg){
        super(msg);
    }
}
