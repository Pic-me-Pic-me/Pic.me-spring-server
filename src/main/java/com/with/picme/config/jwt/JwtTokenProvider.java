package com.with.picme.config.jwt;

import com.with.picme.common.exception.TokenException;
import com.with.picme.common.message.ErrorMessage;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
    @Value("${spring.jwt.secretKey}")
    private String JWT_SECRET_KEY;
    private final ZoneId KST = ZoneId.of("Asia/Seoul");

    public String generateAccessToken(Authentication authentication) {
        val accessExpireTime = new Date().toInstant().atZone(KST).toLocalDateTime().plusHours(2).atZone(KST).toInstant();
        return Jwts.builder()
                .setSubject(String.valueOf(authentication.getPrincipal()))
                .setExpiration(Date.from(accessExpireTime))
                .signWith(getSignKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateRefreshToken(Authentication authentication) {
        val refreshExpireTime = new Date().toInstant().atZone(KST).toLocalDateTime().plusDays(14).atZone(KST).toInstant();
        return Jwts.builder()
                .setSubject(String.valueOf(authentication.getPrincipal()))
                .setExpiration(Date.from(refreshExpireTime))
                .signWith(getSignKey(), SignatureAlgorithm.HS512)
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
