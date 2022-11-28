package com.symphony.hotelchallenge.model;

import javax.persistence.*;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Review {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    // the user that created the review
    private User author;
    private Double hotelRating;
    @Enumerated(value = EnumType.STRING)
    Rating rating;
    // description needs more than 255 chars
    @Column(columnDefinition = "varchar(1000)")
    private String description;
    @OneToMany(cascade = CascadeType.ALL)
    // list of likes in the review
    private List<Likes> likes;
    @OneToMany(cascade = CascadeType.ALL)
    // list of dislikes in the review
    private List<Dislikes> dislikes;

    // list of user usernames that have liked or disliked the review,
    // mainly used for hiding and showing buttons in html part
    @ElementCollection
    private List<String> usersThatLiked;
    @ElementCollection
    private List<String> usersThatDisliked;

    public Review(User author, Rating rating, String description) {
        this.author = author;
        // rating is an enumeration so it needs to be interpreted
        switch (rating) {
            case ONE -> this.hotelRating = 1.0;
            case TWO -> this.hotelRating = 2.0;
            case THREE -> this.hotelRating = 3.0;
            case FOUR -> this.hotelRating = 4.0;
            case FIVE -> this.hotelRating = 5.0;
            default -> this.hotelRating = 0.0;
        }
        this.description = description;
        this.likes = new ArrayList<>();
        this.dislikes = new ArrayList<>();
        this.usersThatLiked = new ArrayList<>();
        this.usersThatDisliked = new ArrayList<>();
    }

    public Review() {
    }

}
