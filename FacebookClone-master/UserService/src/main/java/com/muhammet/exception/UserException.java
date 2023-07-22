package com.muhammet.exception;

import lombok.Getter;

@Getter
public class UserException extends RuntimeException{
    private final ErrorType errorType;
    public UserException(ErrorType errorType){
        super(errorType.getMessage());
        this.errorType=errorType;
    }

    public UserException(ErrorType errorType, String message){
        super(message);
        this.errorType=errorType;
    }
}
