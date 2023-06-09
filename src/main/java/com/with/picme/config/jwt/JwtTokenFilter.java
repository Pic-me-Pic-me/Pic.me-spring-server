package com.with.picme.config.jwt;

import com.with.picme.common.exception.TokenException;
import com.with.picme.common.message.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (!uri.startsWith("/auth") && !uri.contains("common") && !uri.startsWith("/sticker") && !uri.startsWith("/health")) {
            String accessToken = getToken(request);
            JwtTokenType jwtTokenType = jwtTokenProvider.validateToken(accessToken);
            if (StringUtils.hasText(accessToken) && jwtTokenType.equals(JwtTokenType.VALID_TOKEN)) {
                Long userId = jwtTokenProvider.getUsernameFromToken(accessToken);
                UserAuthentication authentication = new UserAuthentication(userId, null, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }
        throw new TokenException(ErrorMessage.EMPTY_TOKEN.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
