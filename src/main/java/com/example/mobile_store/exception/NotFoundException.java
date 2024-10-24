package com.example.mobile_store.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(int id) {
        super("Not found with id: " + id);
    }

    //not found for array
    public NotFoundException(String ex) {
        super(ex);
    }
}
