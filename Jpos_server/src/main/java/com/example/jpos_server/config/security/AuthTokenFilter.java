package com.example.jpos_server.config.security;

import com.example.jpos_server.config.security.jwt.JwtUtils;
import com.example.jpos_server.service.UserAccountDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired private JwtUtils jwtUtils;

    @Autowired private UserAccountDetailsService userAccountDetailsService;

    //jwt 토큰 생성 및 검증
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String jwt = parseJwt(request);
            log.info("1 = {}", jwt);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)){
                String loginId = jwtUtils.getUserNameFromJwtToken(jwt);
                log.info("2 = {}", loginId);
                UserDetails userDetails = userAccountDetailsService.loadUserByUsername(loginId);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        }catch (Exception e){
            throw new IllegalStateException("Cannot set user authentication");
        }
        filterChain.doFilter(request, response);
    }

    // Authorization Header에서 Token을 추출
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}
