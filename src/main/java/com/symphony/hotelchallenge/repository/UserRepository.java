package com.symphony.hotelchallenge.repository;

import com.symphony.hotelchallenge.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//JpaRepository class helps generate methods that are later used in the service classes
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameAndPassword(String username, String password);

    User findByUsername(String username);

    User findUserByEmail(String email);
}
