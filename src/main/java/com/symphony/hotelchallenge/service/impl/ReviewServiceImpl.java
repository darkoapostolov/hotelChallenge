package com.symphony.hotelchallenge.service.impl;

import com.symphony.hotelchallenge.model.*;
import com.symphony.hotelchallenge.model.exceptions.InvalidAccountIdException;
import com.symphony.hotelchallenge.model.exceptions.InvalidReviewIdException;
import com.symphony.hotelchallenge.repository.DislikesRepository;
import com.symphony.hotelchallenge.repository.LikesRepository;
import com.symphony.hotelchallenge.repository.ReviewRepository;
import com.symphony.hotelchallenge.repository.UserRepository;
import com.symphony.hotelchallenge.service.ReviewService;
import org.springframework.stereotype.Service;

// implements service interface
@Service
public class ReviewServiceImpl implements ReviewService {

    // dependency injection
    private final ReviewRepository repository;
    private final UserRepository userRepository;
    private final LikesRepository likesRepository;
    private final DislikesRepository dislikesRepository;

    public ReviewServiceImpl(ReviewRepository repository, UserRepository userRepository, LikesRepository likesRepository, DislikesRepository dislikesRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.likesRepository = likesRepository;
        this.dislikesRepository = dislikesRepository;
    }

    // finds review by id
    @Override
    public Review findById(Long id) throws InvalidReviewIdException {
        return repository.findById(id).orElseThrow(InvalidReviewIdException::new);
    }

    // creates new instance of Review class
    @Override
    public Review create(User author, Rating rating, String description) {
        Review review = new Review(author, rating, description);
        repository.save(review);
        return review;
    }

    // finds review by id, then edits values with setters
    @Override
    public Review edit(Long id, User author, Rating rating, String description) throws InvalidReviewIdException {
        Review review = findById(id);
        review.setAuthor(author);
        review.setRating(rating);
        switch (rating) {
            case ONE -> review.setHotelRating(1.0);
            case TWO -> review.setHotelRating(2.0);
            case THREE -> review.setHotelRating(3.0);
            case FOUR -> review.setHotelRating(4.0);
            case FIVE -> review.setHotelRating(5.0);
            default -> review.setHotelRating(0.0);
        }
        review.setDescription(description);
        repository.save(review);
        return review;
    }

    // finds review and user by id, checks if like already exists,
    // either creates new like or uses one that already exits and adds it to the review
    @Override
    public Integer like(Long id, Long userId) throws InvalidReviewIdException, InvalidAccountIdException {
        Review review = findById(id);
        User user = userRepository.findById(userId).orElseThrow(InvalidAccountIdException::new);
        if (likesRepository.findByUserAndReview(user, review) != null && !review.getLikes().contains(likesRepository.findByUserAndReview(user, review))) {
            review.getLikes().add(likesRepository.findByUserAndReview(user, review));
        } else {
            review.getLikes().add(new Likes(user, review));
        }
        repository.save(review);
        return review.getLikes().size();
    }

    // finds review and user by id, removes like from the review
    @Override
    public Integer unlike(Long id, Long userId) throws InvalidReviewIdException, InvalidAccountIdException {
        Review review = findById(id);
        User user = userRepository.findById(userId).orElseThrow(InvalidAccountIdException::new);
        Likes like = likesRepository.findByUserAndReview(user, review);
        review.getLikes().remove(like);
        repository.save(review);
        return review.getLikes().size();
    }

    // finds review and user by id, checks if dislike already exists,
    // either creates new dislike or uses one that already exits and adds it to the review
    @Override
    public Integer dislike(Long id, Long userId) throws InvalidReviewIdException, InvalidAccountIdException {
        Review review = findById(id);
        User user = userRepository.findById(userId).orElseThrow(InvalidAccountIdException::new);
        Dislikes dislike = new Dislikes(user, review);
        review.getDislikes().add(dislike);
        repository.save(review);
        return review.getDislikes().size();
    }

    // finds review and user by id, removes dislike from the review
    @Override
    public Integer unDislike(Long id, Long userId) throws InvalidReviewIdException, InvalidAccountIdException {
        Review review = findById(id);
        User user = userRepository.findById(userId).orElseThrow(InvalidAccountIdException::new);
        Dislikes dislike = dislikesRepository.findByUserAndReview(user, review);
        review.getDislikes().remove(dislike);
        repository.save(review);
        return review.getDislikes().size();
    }

}
