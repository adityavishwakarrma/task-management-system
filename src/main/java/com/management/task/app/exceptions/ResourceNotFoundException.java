package com.management.task.app.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RuntimeException{
    private String message;
    private HttpStatus status;

    public ResourceNotFoundException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }
    public ResourceNotFoundException() {
    }
}
