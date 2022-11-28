package com.symphony.hotelchallenge.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Likes {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    // user that created the like
    private User user;
    @OneToOne(cascade = CascadeType.ALL)
    // review that was liked
    private Review review;

    public Likes(User user, Review review) {
        this.user = user;
        this.review = review;
    }

    public Likes() {
    }
}
