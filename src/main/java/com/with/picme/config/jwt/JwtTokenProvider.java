package com.with.picme.config.jwt;

import com.with.picme.common.exception.TokenException;
import com.with.picme.common.message.ErrorMessage;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
    @Value("${spring.jwt.secretKey}")
    private String JWT_SECRET_KEY;
    private static final long ACCESS_TOKEN_EXPIRATION_TIME = Duration.ofMinutes(30).toMillis();
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = Duration.ofDays(14).toMillis();

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

    private Date getExpireDate(long time) {
        Date date = new Date();
        return new Date(date.getTime() + time);
    }

    private Key getSignKey() {
        byte[] keyBytes = JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtTokenType validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
            return JwtTokenType.VALID_TOKEN;
        } catch (SecurityException | MalformedJwtException e) {
            throw new TokenException(ErrorMessage.INVALID_SIGNATURE.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ExpiredJwtException e) {
            throw new TokenException(ErrorMessage.EXPIRED_TOKEN.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (UnsupportedJwtException e) {
            throw new TokenException(ErrorMessage.UNSUPPORTED_TOKEN.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            throw new TokenException(ErrorMessage.EMPTY_TOKEN.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
