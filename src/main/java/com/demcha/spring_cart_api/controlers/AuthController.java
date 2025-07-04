package com.demcha.spring_cart_api.controlers;

import com.demcha.spring_cart_api.config.JwtConfig;
import com.demcha.spring_cart_api.dtos.JwtResponse;
import com.demcha.spring_cart_api.dtos.LoginRequest;
import com.demcha.spring_cart_api.dtos.UserDto;
import com.demcha.spring_cart_api.entities.User;
import com.demcha.spring_cart_api.mappers.UserMapper;
import com.demcha.spring_cart_api.repositories.UserRepository;
import com.demcha.spring_cart_api.services.AuthService;
import com.demcha.spring_cart_api.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JwtConfig jwtConfig;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request, HttpServletResponse response) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        var user = userRepository.findUserByEmail(request.email()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String accessToken = jwtService.generateAccessToken(user).toString();
        String refreshToken = jwtService.generateRefreshToken(user).toString();

        var cookie = new Cookie("refreshToken", refreshToken);

        cookie.setHttpOnly(true);
        cookie.setPath("/auth/refresh");
        cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration()); //7d
        cookie.setSecure(true);
        response.addCookie(cookie);


        return ResponseEntity.ok(new JwtResponse(accessToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@CookieValue(value = "refreshToken") String refreshToken) {
        var jwt = jwtService.parseToken(refreshToken);

        if (jwt == null || jwt.isExpired()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        var user = userRepository.findById(jwt.getUserId()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var accessToken = jwtService.generateAccessToken(user);

        return ResponseEntity.ok(new JwtResponse(accessToken.toString()));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me() {


        User user = authService.getCurrentUser();
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        var userDto = userMapper.toDto(user);
        return ResponseEntity.ok(userDto);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadCredentialException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
