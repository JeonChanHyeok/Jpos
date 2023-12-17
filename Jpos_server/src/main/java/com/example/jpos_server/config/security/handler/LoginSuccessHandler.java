package com.example.jpos_server.config.security.handler;

import com.example.jpos_server.config.security.CustomStoreDetailsService;
import com.example.jpos_server.domain.Store;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> responseMap = new HashMap<>();

        Store store = (Store) authentication.getPrincipal();

        String message = store.getStoreLoginId();

        responseMap.put("status", 201);

        responseMap.put("message", message);

        response.getOutputStream().println(objectMapper.writeValueAsString(responseMap));
    }
}
