package com.symphony.hotelchallenge.service;

import com.symphony.hotelchallenge.model.User;
import com.symphony.hotelchallenge.model.Hotel;
import com.symphony.hotelchallenge.model.Review;
import com.symphony.hotelchallenge.model.exceptions.InvalidAccountIdException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {
    List<User> listAll();

    User findById(Long id) throws InvalidAccountIdException;

    void addReview(Review review, Long id) throws InvalidAccountIdException;

    void addToFavorites(Hotel hotel, Long id) throws InvalidAccountIdException;

    void removeFromFavorites(Hotel hotel, Long id) throws InvalidAccountIdException;

    User findByUsername(String username);

    User findByEmail(String email);

    User register(String email, String username, String password);
}
