package com.demcha.spring_cart_api.services;

import com.demcha.spring_cart_api.entities.User;
import com.demcha.spring_cart_api.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {


    private final UserRepository userRepository;


    private User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found exception"));

    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        var user = getUserByEmail(email);
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), Collections.emptyList());

    }
}
