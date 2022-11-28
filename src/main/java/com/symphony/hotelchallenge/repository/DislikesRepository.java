package com.symphony.hotelchallenge.repository;

import com.symphony.hotelchallenge.model.Dislikes;
import com.symphony.hotelchallenge.model.Review;
import com.symphony.hotelchallenge.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DislikesRepository extends JpaRepository<Dislikes, Long> {
    Dislikes findByUserAndReview(User user, Review review);
}
