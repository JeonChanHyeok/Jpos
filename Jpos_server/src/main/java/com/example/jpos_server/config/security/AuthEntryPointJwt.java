package com.example.jpos_server.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (request.getRequestURI().equals("/error")) {
            final ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseMap = new HashMap<String, Object>();
            responseMap.put("message", "권한이 없습니다.");
            responseMap.put("status", 460);

            response.setCharacterEncoding("utf-8");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(460);

            try (ServletOutputStream os = response.getOutputStream()) {
                objectMapper.writeValue(os, responseMap);
                os.flush();
            }
        } else {
            response.sendRedirect("/sign-in");
        }
    }
}
