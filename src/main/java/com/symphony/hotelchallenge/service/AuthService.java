package com.symphony.hotelchallenge.service;

import com.symphony.hotelchallenge.model.User;

public interface AuthService {
    User login(String username, String password);
}
