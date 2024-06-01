package com.katas.mower.exceptions;

public class OutOfFieldDimensionException extends RuntimeException {

    public OutOfFieldDimensionException(String errorMessage){
        super(errorMessage);
    }
}