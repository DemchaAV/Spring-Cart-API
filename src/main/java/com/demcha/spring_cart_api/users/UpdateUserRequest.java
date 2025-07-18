package com.demcha.spring_cart_api.users;

import lombok.Data;

@Data
public class UpdateUserRequest {
    public String name;
    public String email;
}
