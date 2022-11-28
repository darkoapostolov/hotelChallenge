package com.symphony.hotelchallenge.model.exceptions;

// Is thrown when searching for reviews by id, if the id does not exist
public class InvalidReviewIdException extends Exception {
    public InvalidReviewIdException() {
        super("Invalid Review Id");
    }
}
