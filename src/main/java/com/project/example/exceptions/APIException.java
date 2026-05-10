package com.project.example.exceptions;

//ApiException used for checking the item already exist or not if it exists handles here
public class APIException extends RuntimeException{

    private static final long serialVersionUID=1L;

    public APIException() {
    }

    public APIException(String message) {
        super(message);
    }
}
