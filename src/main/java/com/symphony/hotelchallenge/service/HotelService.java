package com.symphony.hotelchallenge.service;

import com.symphony.hotelchallenge.model.Hotel;
import com.symphony.hotelchallenge.model.Review;
import com.symphony.hotelchallenge.model.exceptions.InvalidHotelIdException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HotelService {
    List<Hotel> listAll();

    Hotel findById(Long id) throws InvalidHotelIdException;

    Hotel create(String hotelName, String address, String image, String description, String geolocation);

    Hotel edit(Long id, String hotelName, String address, String image, String description, String geolocation) throws InvalidHotelIdException;

    Hotel delete(Long id) throws InvalidHotelIdException;

    List<Hotel> listSorted();

    void addReview(Long id, Review review) throws InvalidHotelIdException;

    List<Review> getReviews(Hotel hotel);

    Double calculateHotelRating(Hotel hotel);

    List<Hotel> filterHotels(String hotelName, String address);
}
