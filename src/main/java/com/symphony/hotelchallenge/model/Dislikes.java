package com.symphony.hotelchallenge.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Dislikes {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    // the user that created the dislike
    private User user;
    @OneToOne(cascade = CascadeType.ALL)
    // the review that was disliked
    private Review review;

    public Dislikes(User user, Review review) {
        this.user = user;
        this.review = review;
    }

    public Dislikes() {
    }
}
