package com.demcha.spring_cart_api.mappers;


import com.demcha.spring_cart_api.dtos.RegisterUserRequest;
import com.demcha.spring_cart_api.dtos.UpdateUserRequest;
import com.demcha.spring_cart_api.dtos.UserDto;
import com.demcha.spring_cart_api.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "createAt", expression = "java(java.time.LocalDateTime.now())")
    UserDto toDto(User user);

    User toEntity(RegisterUserRequest request);
    void updateEntity(UpdateUserRequest request,@MappingTarget User user);
}
