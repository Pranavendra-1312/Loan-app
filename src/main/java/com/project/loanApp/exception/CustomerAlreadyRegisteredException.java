package com.project.loanApp.exception;

public class CustomerAlreadyRegisteredException extends RuntimeException{
    public CustomerAlreadyRegisteredException(String msg){
        super(msg);
    }
}
