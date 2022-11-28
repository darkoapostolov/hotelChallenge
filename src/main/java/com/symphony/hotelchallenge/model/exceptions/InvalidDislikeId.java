package com.symphony.hotelchallenge.model.exceptions;

// Is thrown when searching for dislikes by id, if the id does not exist
public class InvalidDislikeId extends Exception {
    public InvalidDislikeId() {
        super("Invalid Dislike Id");
    }
}
