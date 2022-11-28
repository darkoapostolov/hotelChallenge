package com.symphony.hotelchallenge.model;

import org.springframework.security.core.GrantedAuthority;

// the roles that can be acquired by a user,
// users are by default ROLE_USER,
// the only way to change a users role is through editing the database
public enum Role implements GrantedAuthority {

    ROLE_USER, ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
