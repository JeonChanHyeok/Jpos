package com.example.jpos_server.config.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Getter
@ConfigurationProperties(prefix = "jwt")
@AllArgsConstructor
public class JwtProperties {
    private String secretKey;
    private int expiration;
}
