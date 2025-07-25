package com.demcha.spring_cart_api.auth;

import com.demcha.spring_cart_api.users.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class JwtService {

    private final JwtConfig jwtConfig;


    public Jwt generateAccessToken(User user) {


        return generateAccessToken(user, jwtConfig.getAccessTokenExpiration());
    }

    public Jwt generateRefreshToken(User user) {


        return generateAccessToken(user, jwtConfig.getRefreshTokenExpiration());
    }


    private Jwt generateAccessToken(User user, long tokenExpiration) {


        var claims = Jwts.claims().subject(user.getId().toString())
                .add("email", user.getEmail())
                .add("name", user.getName())
                .add("role", user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
                .build();
        return new Jwt(claims, jwtConfig.getSecret());


    }

    public Jwt parseToken(String token) {
        try {
            Claims claims = getClaims(token);
            return new Jwt(claims, jwtConfig.getSecret());
        } catch (Exception e) {
            return null;
        }

    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtConfig.getSecret())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


}
