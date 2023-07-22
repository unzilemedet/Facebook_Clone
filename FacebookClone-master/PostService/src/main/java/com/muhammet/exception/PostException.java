package com.muhammet.exception;

import lombok.Getter;

@Getter
public class PostException extends RuntimeException{
    private final ErrorType errorType;
    public PostException(ErrorType errorType){
        super(errorType.getMessage());
        this.errorType=errorType;
    }

    public PostException(ErrorType errorType, String message){
        super(message);
        this.errorType=errorType;
    }
}
