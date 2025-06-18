package com.demcha.spring_cart_api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateUserPassword {
    @JsonProperty(namespace = "oldPassword")
    private String oldPassword;
    @JsonProperty(namespace = "newPassword")
    private String newPassword;

}
