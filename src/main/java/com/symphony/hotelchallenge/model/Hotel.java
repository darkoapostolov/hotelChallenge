package com.symphony.hotelchallenge.model;

import javax.persistence.*;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Hotel {

    @Id
    @GeneratedValue
    private Long id;
    private String hotelName;
    private String address;
    private String image;
    // description needs more space than 255 chars
    @Column(columnDefinition = "varchar(3000)")
    private String description;
    private String geolocation;
    private Double rating;
    @OneToMany
    // reviews located in the details section
    private List<Review> reviews;
    @ElementCollection
    // used to determine which users have placed the hotel in their favorites, mainly for html part to hide the button
    private List<String> usersThatHaveHotelInFavorites;

    public Hotel(String hotelName, String address, String image, String description, String geolocation) {
        this.hotelName = hotelName;
        this.address = address;
        this.image = image;
        this.description = description;
        this.geolocation = geolocation;
        this.rating = 0.0;
        this.reviews = new ArrayList<>();
        this.usersThatHaveHotelInFavorites = new ArrayList<>();
    }

    public Hotel() {
    }

}
