package com.symphony.hotelchallenge.model.exceptions;

// Is thrown if the input arguments are not valid while authenticating
public class InvalidArgumentsException extends RuntimeException {
    public InvalidArgumentsException() {
        super("Invalid arguments exception");
    }
}
