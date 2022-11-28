package com.symphony.hotelchallenge.model.exceptions;

// Is thrown when searching for users by id, if the id does not exist
public class InvalidAccountIdException extends Exception {
    public InvalidAccountIdException() {
        super("Invalid Account Id");
    }
}
