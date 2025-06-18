package com.demcha.spring_cart_api.repositories;

import com.demcha.spring_cart_api.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}