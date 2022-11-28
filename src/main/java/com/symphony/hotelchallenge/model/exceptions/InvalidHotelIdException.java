package com.symphony.hotelchallenge.model.exceptions;

// Is thrown when searching for hotels by id, if the id does not exist
public class InvalidHotelIdException extends Exception {
    public InvalidHotelIdException() {
        super("Invalid Hotel Id");
    }
}
