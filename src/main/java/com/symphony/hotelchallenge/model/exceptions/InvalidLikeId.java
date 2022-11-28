package com.symphony.hotelchallenge.model.exceptions;

// Is thrown when searching for likes by id, if the id does not exist
public class InvalidLikeId extends Exception {
    public InvalidLikeId() {
        super("Invalid Like Id");
    }
}
