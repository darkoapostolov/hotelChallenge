package com.symphony.hotelchallenge.repository;

import com.symphony.hotelchallenge.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findAllByHotelNameLike(String hotelName);

    List<Hotel> findAllByAddressLike(String address);

    List<Hotel> findAllByHotelNameLikeAndAddressLike(String hotelName, String address);
}
