package com.symphony.hotelchallenge.service.impl;

import com.symphony.hotelchallenge.model.Hotel;
import com.symphony.hotelchallenge.model.Review;
import com.symphony.hotelchallenge.model.exceptions.InvalidHotelIdException;
import com.symphony.hotelchallenge.repository.HotelRepository;
import com.symphony.hotelchallenge.service.HotelService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

//implements service interface
@Service
public class HotelServiceImpl implements HotelService {

    //dependency injection
    private final HotelRepository repository;

    public HotelServiceImpl(HotelRepository repository) {
        this.repository = repository;
    }

    // lists all hotels
    @Override
    public List<Hotel> listAll() {
        return repository.findAll();
    }

    // finds hotel by id
    @Override
    public Hotel findById(Long id) throws InvalidHotelIdException {
        return repository.findById(id).orElseThrow(InvalidHotelIdException::new);
    }

    // creates a new instance of the Hotel class
    @Override
    public Hotel create(String hotelName, String address, String image, String description, String geolocation) {
        Hotel hotel = new Hotel(hotelName, address, image, description, geolocation);
        repository.save(hotel);
        return hotel;
    }

    // edits a hotel by finding it by its id, then using setters to change its values
    @Override
    public Hotel edit(Long id, String hotelName, String address, String image, String description, String geolocation) throws InvalidHotelIdException {
        Hotel hotel = findById(id);
        hotel.setHotelName(hotelName);
        hotel.setAddress(address);
        hotel.setImage(image);
        hotel.setDescription(description);
        hotel.setGeolocation(geolocation);
        repository.save(hotel);
        return hotel;
    }

    // deletes particular instance of Hotel class, hotel is found by id, then deleted
    @Override
    public Hotel delete(Long id) throws InvalidHotelIdException {
        Hotel hotel = findById(id);
        repository.delete(hotel);
        return hotel;
    }

    // sorts the list of hotels by name ascending
    @Override
    public List<Hotel> listSorted() {
        Comparator<Hotel> hotelComparable = Comparator.comparing(Hotel::getHotelName).thenComparing(Hotel::getRating);
        return listAll().stream().sorted(hotelComparable).collect(Collectors.toList());
    }

    // adds a review to the hotel
    @Override
    public void addReview(Long id, Review review) throws InvalidHotelIdException {
        Hotel hotel = repository.findById(id).orElseThrow(InvalidHotelIdException::new);
        hotel.getReviews().add(review);
        Double hotelRating = calculateHotelRating(hotel);
        hotel.setRating(hotelRating);
        repository.save(hotel);
    }

    // lists all reviews in the hotel
    @Override
    public List<Review> getReviews(Hotel hotel) {
        return hotel.getReviews();
    }

    // Formula:
    // Rating/number of ratings
    // + 0.01 or -0.01 from positive and negative reviews respectively
    // + number of users that have the hotel in their favorites * 0.01
    @Override
    public Double calculateHotelRating(Hotel hotel) {
        Double rating = getReviews(hotel).stream().mapToDouble(f -> f.getHotelRating()).sum();
        Double hotelRating = rating / hotel.getReviews().size();
        for (int i = 0; i < hotel.getReviews().size(); i++) {
            Review review = hotel.getReviews().get(i);
            if (review.getHotelRating() > 3.0) {
                if (review.getLikes().size() > review.getDislikes().size()) {
                    hotelRating += 0.01;
                    //Every 100 positive reviews +1
                }
            } else {
                if (review.getLikes().size() > review.getDislikes().size()) {
                    hotelRating -= 0.01;
                    //Every 100 negative reviews -1
                }
            }
        }
        hotelRating += hotel.getUsersThatHaveHotelInFavorites().size() * 0.01;
        //Every 100 users that have the hotel in favorites +1
        return hotelRating;
    }

    // Search function, filters hotels by name and/or address
    @Override
    public List<Hotel> filterHotels(String hotelName, String address) {
        if (hotelName == null && address == null) {
            return listSorted();
        } else if (hotelName != null && address != null) {
            return repository.findAllByHotelNameLikeAndAddressLike("%" + hotelName + "%", "%" + address + "%");
        } else if (hotelName != null) {
            return repository.findAllByHotelNameLike("%" + hotelName + "%");
        } else {
            return repository.findAllByAddressLike("%" + address + "%");
        }
    }

}
