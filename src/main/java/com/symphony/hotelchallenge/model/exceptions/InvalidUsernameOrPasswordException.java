package com.symphony.hotelchallenge.model.exceptions;

// Is thrown if username or password is not valid
public class InvalidUsernameOrPasswordException extends RuntimeException {
    public InvalidUsernameOrPasswordException() {
        super("Invalid Username or Password");
    }
}
