package com.katas.mower.exceptions;

public class InvalidMowerJobFileInputException extends RuntimeException {

    public InvalidMowerJobFileInputException(String errorMessage){
        super(errorMessage);
    }
}