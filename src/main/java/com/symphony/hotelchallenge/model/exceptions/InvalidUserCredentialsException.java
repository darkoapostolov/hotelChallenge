package com.symphony.hotelchallenge.model.exceptions;

// Is thrown if input while logging in is not correct
public class InvalidUserCredentialsException extends RuntimeException {
    public InvalidUserCredentialsException() {
        super("Invalid user credentials exception");
    }
}
