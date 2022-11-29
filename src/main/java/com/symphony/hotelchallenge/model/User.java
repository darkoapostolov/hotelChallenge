package com.symphony.hotelchallenge.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@Table(name = "app_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String email;
    // only one user can have a certain username
    @Column(unique = true)
    private String username;
    private String password;
    // reviews that a user has made
    @OneToMany
    private List<Review> reviews;
    // list of the users favorite hotels
    @OneToMany
    private List<Hotel> favorites;

    // booleans for the implementation functions
    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;

    // the users role, default is ROLE_USER
    @Enumerated(value = EnumType.STRING)
    private Role role;

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.reviews = new ArrayList<>();
        this.favorites = new ArrayList<>();
        this.role = Role.ROLE_USER;
    }

    public User() {
    }


    // functions implemented by the interface
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(role);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
