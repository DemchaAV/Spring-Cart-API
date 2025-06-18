package com.demcha.spring_cart_api.repositories;

import com.demcha.spring_cart_api.entities.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
}