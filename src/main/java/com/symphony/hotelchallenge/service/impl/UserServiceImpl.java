package com.symphony.hotelchallenge.service.impl;

import com.symphony.hotelchallenge.model.User;
import com.symphony.hotelchallenge.model.Hotel;
import com.symphony.hotelchallenge.model.Review;
import com.symphony.hotelchallenge.model.exceptions.InvalidAccountIdException;
import com.symphony.hotelchallenge.model.exceptions.InvalidUsernameOrPasswordException;
import com.symphony.hotelchallenge.model.exceptions.UsernameAlreadyExistsException;
import com.symphony.hotelchallenge.repository.HotelRepository;
import com.symphony.hotelchallenge.repository.UserRepository;
import com.symphony.hotelchallenge.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

// implements service interface
@Service
public class UserServiceImpl implements UserService {

    // dependency injection
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final HotelRepository hotelRepository;

    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder, HotelRepository hotelRepository) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.hotelRepository = hotelRepository;
    }

    // lists all users
    @Override
    public List<User> listAll() {
        return repository.findAll();
    }

    // finds user by id
    @Override
    public User findById(Long id) throws InvalidAccountIdException {
        return repository.findById(id).orElseThrow(InvalidAccountIdException::new);
    }

    // adds review that user has made to their account
    @Override
    public void addReview(Review review, Long id) throws InvalidAccountIdException {
        User user = findById(id);
        user.getReviews().add(review);
        repository.save(user);
    }

    // adds hotel to users favorites
    @Override
    public void addToFavorites(Hotel hotel, Long id) throws InvalidAccountIdException {
        User user = findById(id);
        user.getFavorites().add(hotel);
        hotelRepository.save(hotel);
        repository.save(user);
    }

    // removes hotel from users favorites
    @Override
    public void removeFromFavorites(Hotel hotel, Long id) throws InvalidAccountIdException {
        User user = findById(id);
        user.getFavorites().remove(hotel);
        hotelRepository.save(hotel);
        repository.save(user);
    }

    // finds user by username
    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }

    // registers user, creates new instance of User class
    @Override
    public User register(String email, String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty() || email == null || email.isEmpty())
            throw new InvalidUsernameOrPasswordException();
        if (this.repository.findByUsername(username) != null)
            throw new UsernameAlreadyExistsException(username);
        User user = new User(email, username, passwordEncoder.encode(password));
        return repository.save(user);
    }

    // pretty much the same as find by username, used in authentication
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) repository.findByUsername(username);
    }
}
