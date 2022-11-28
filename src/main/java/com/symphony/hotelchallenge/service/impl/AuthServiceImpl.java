package com.symphony.hotelchallenge.service.impl;

import com.symphony.hotelchallenge.model.User;
import com.symphony.hotelchallenge.model.exceptions.InvalidArgumentsException;
import com.symphony.hotelchallenge.repository.UserRepository;
import com.symphony.hotelchallenge.service.AuthService;
import org.springframework.stereotype.Service;

//implements service interface
@Service
public class AuthServiceImpl implements AuthService {

    //dependency injection
    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // login method
    @Override
    public User login(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        return userRepository.findByUsernameAndPassword(username,
                password);
    }

}
