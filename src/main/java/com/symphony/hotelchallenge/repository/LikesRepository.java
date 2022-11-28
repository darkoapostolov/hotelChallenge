package com.symphony.hotelchallenge.repository;

import com.symphony.hotelchallenge.model.Likes;
import com.symphony.hotelchallenge.model.Review;
import com.symphony.hotelchallenge.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    Likes findByUserAndReview(User user, Review review);
}
