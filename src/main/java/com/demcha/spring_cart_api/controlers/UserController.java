package com.demcha.spring_cart_api.controlers;


import com.demcha.spring_cart_api.dtos.RegisterUserRequest;
import com.demcha.spring_cart_api.dtos.UpdateUserPassword;
import com.demcha.spring_cart_api.dtos.UpdateUserRequest;
import com.demcha.spring_cart_api.dtos.UserDto;
import com.demcha.spring_cart_api.entities.Role;
import com.demcha.spring_cart_api.entities.User;
import com.demcha.spring_cart_api.mappers.UserMapper;
import com.demcha.spring_cart_api.repositories.UserRepository;
import com.demcha.spring_cart_api.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;


    @GetMapping
    public List<UserDto> getAllUsers(
            @RequestHeader(name = "x-auth-token", required = false) String authToken,
            @RequestParam(name = "sort", required = false, defaultValue = "") String sortBy

    ) {
        System.out.println(authToken);

        if (!Set.of("name", "email").contains(sortBy)) {
            sortBy = "name";
        }
        return userRepository.findAll(Sort.by(sortBy)).stream().map(userMapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build(); // Return a 404 (Not Found)
        } else {
            var userDto = userMapper.toDto(user);
            return ResponseEntity.ok(userDto);
        }
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterUserRequest request,
                                          UriComponentsBuilder uriComponentsBuilder) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body(Map.of("email", "Email already registered"));
        }
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        var userDto = userMapper.toDto(user);
        var uri = uriComponentsBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).body(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable(name = "id") Long id, @RequestBody UpdateUserRequest request) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        userMapper.updateEntity(request, user);
        userRepository.save(user);

        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "id") Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(@PathVariable(name = "id") Long id, @RequestBody UpdateUserPassword request) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        if (user.getPassword().equals(request.getOldPassword())) {
            user.setPassword(request.getNewPassword());
            userRepository.save(user);
            return ResponseEntity.ok().build();
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }


}
