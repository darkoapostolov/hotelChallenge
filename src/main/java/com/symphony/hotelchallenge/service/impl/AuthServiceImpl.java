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

    // login method, to enable login with email and password,
    // the input string(the email) is used to find the username
    @Override
    public User login(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        String username = userRepository.findUserByEmail(email).getUsername();
        return userRepository.findByUsernameAndPassword(username,
                password);
    }

}
