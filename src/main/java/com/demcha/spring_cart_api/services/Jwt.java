package com.demcha.spring_cart_api.services;

import com.demcha.spring_cart_api.entities.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@AllArgsConstructor
@ToString
public class Jwt {
    private final Claims claims;
    private final SecretKey secretKey;

    @Bean
    public boolean isExpired() {
        return claims.getExpiration().before(new Date());
    }

    public Long getUserId() {
        return Long.valueOf(claims.getSubject());
    }

    public Role getRole() {
        return Role.valueOf(claims.get("role", String.class));
    }

    @Override
    public String toString(){
        return Jwts.builder().claims(claims).signWith(secretKey).compact();
    }

}
