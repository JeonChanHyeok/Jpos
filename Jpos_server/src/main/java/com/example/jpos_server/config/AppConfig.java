package com.example.jpos_server.config;

import com.example.jpos_server.config.security.jwt.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


// application.yml의 jwt 바인딩이 안되서 찾아보니 이렇게 설정을 따로 해줘야 한다고 함.
@Configuration
@EnableConfigurationProperties({JwtProperties.class})
public class AppConfig {
}
