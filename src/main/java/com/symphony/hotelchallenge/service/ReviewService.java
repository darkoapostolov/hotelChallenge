package com.symphony.hotelchallenge.service;

import com.symphony.hotelchallenge.model.Rating;
import com.symphony.hotelchallenge.model.User;
import com.symphony.hotelchallenge.model.Review;
import com.symphony.hotelchallenge.model.exceptions.InvalidAccountIdException;
import com.symphony.hotelchallenge.model.exceptions.InvalidDislikeId;
import com.symphony.hotelchallenge.model.exceptions.InvalidLikeId;
import com.symphony.hotelchallenge.model.exceptions.InvalidReviewIdException;
import org.springframework.stereotype.Service;

@Service
public interface ReviewService {
    Review findById(Long id) throws InvalidReviewIdException;

    Review create(User author, Rating rating, String description);

    Review edit(Long id, User author, Rating rating, String description) throws InvalidReviewIdException;

    Integer like(Long id, Long userId) throws InvalidReviewIdException, InvalidAccountIdException;

    Integer unlike(Long id, Long userId) throws InvalidReviewIdException, InvalidAccountIdException, InvalidLikeId;

    Integer dislike(Long id, Long userId) throws InvalidReviewIdException, InvalidAccountIdException;

    Integer unDislike(Long id, Long userId) throws InvalidReviewIdException, InvalidAccountIdException, InvalidDislikeId;
}
