package com.target.myretail.exception;

public class DataInputException extends RuntimeException{
    public DataInputException(String message) {
        super(message);
    }

    public DataInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
