package com.example.jpos_server.config.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            Map<String, Object> responseMap = new HashMap<>();

            String message = getExceptionMessage(exception);

            responseMap.put("status", 401);

            responseMap.put("message", message);

            response.getOutputStream().println(objectMapper.writeValueAsString(responseMap));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String getExceptionMessage(AuthenticationException exception) {
        if (exception instanceof BadCredentialsException) {
            return "Password-Error";
        } else if (exception instanceof UsernameNotFoundException) {
            return "Not-Account";
        } else if (exception instanceof AccountExpiredException) {
            return "3";
        } else if (exception instanceof CredentialsExpiredException) {
            return "4";
        } else if (exception instanceof DisabledException) {
            return "5";
        } else if (exception instanceof LockedException) {
            return "6";
        } else {
            return "7";
        }
    }

}
