package com.symphony.hotelchallenge.model.exceptions;

// Is thrown if the username already exists
public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String username) {
        super(String.format("User with username: %s already exists", username));
    }
}
