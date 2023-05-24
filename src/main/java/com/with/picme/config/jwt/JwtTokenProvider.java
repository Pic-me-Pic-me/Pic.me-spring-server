package com.with.picme.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final String JWT_SECRET_KEY = "";
    private static final int ACCESS_TOKEN_EXPIRATION_TIME = 1000;
    private static final int REFRESH_TOKEN_EXPIRATION_TIME = 1000;

    public String generateAccessToken(Authentication authentication) {
        return Jwts.builder()
                .setSubject(String.valueOf(authentication.getPrincipal()))
                .setExpiration(getExpireDate(ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET_KEY)
                .compact();
    }

    public String generateRefreshToken(Authentication authentication) {
        return Jwts.builder()
                .setSubject(String.valueOf(authentication.getPrincipal()))
                .setExpiration(getExpireDate(REFRESH_TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET_KEY)
                .compact();
    }

    public Long getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return Long.valueOf(claims.getSubject());
    }

    private Date getExpireDate(int time) {
        Date date = new Date();
        return new Date(date.getTime() + time);
    }

    private Key getSignKey() {
        byte[] keyBytes = JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
