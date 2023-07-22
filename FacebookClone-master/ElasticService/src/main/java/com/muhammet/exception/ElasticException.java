package com.muhammet.exception;

import lombok.Getter;

@Getter
public class ElasticException extends RuntimeException{
    private final ErrorType errorType;
    public ElasticException(ErrorType errorType){
        super(errorType.getMessage());
        this.errorType=errorType;
    }

    public ElasticException(ErrorType errorType, String message){
        super(message);
        this.errorType=errorType;
    }
}
