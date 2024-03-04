package com.example.jpos_server.config.security;

import com.example.jpos_server.service.UserAccountDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserAccountDetailsService userAccountDetailsService;
    private final AuthEntryPointJwt authEntryPointJwt;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userAccountDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPointJwt))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth.
                        requestMatchers(
                                new AntPathRequestMatcher("/jpos/profile/signup"),
                                new AntPathRequestMatcher("/jpos/profile/delete/**"),
                                new AntPathRequestMatcher("/jpos/profile/storeInfo/update/**")
                        ).hasAnyRole("OWNER")
                        .requestMatchers(
                                new AntPathRequestMatcher("/jpos/menuSetting/menu/**"),
                                new AntPathRequestMatcher("/jpos/menuSetting/category/**"),
                                new AntPathRequestMatcher("/jpos/seatSetting/add"),
                                new AntPathRequestMatcher("/jpos/seatSetting/delete/**")
                        ).hasAnyRole("OWNER", "MANAGER")
                        .requestMatchers(
                                new AntPathRequestMatcher("/main/**"),
                                new AntPathRequestMatcher("/jpos/dashboard/**"),
                                new AntPathRequestMatcher("/jpos/inOrder/**"),
                                new AntPathRequestMatcher("/jpos/menuSetting/{storeId}"),
                                new AntPathRequestMatcher("/jpos/pos/**"),
                                new AntPathRequestMatcher("/jpos/profile/{loginId}"),
                                new AntPathRequestMatcher("/jpos/qrCode/**"),
                                new AntPathRequestMatcher("/jpos/seatSetting/{storeId}"),
                                new AntPathRequestMatcher("/jpos/store/**")
                        ).hasAnyRole("OWNER", "MANAGER", "CUSTOMER")
                        .requestMatchers(
                                new AntPathRequestMatcher("/menus/**"),
                                new AntPathRequestMatcher("/assets/**"),
                                new AntPathRequestMatcher("/favicon.ico"),
                                new AntPathRequestMatcher("/sign-in"),
                                new AntPathRequestMatcher("/sign-up"),
                                new AntPathRequestMatcher("/qrOrder/**"),
                                new AntPathRequestMatcher("/jpos/qrOrder/**"),
                                new AntPathRequestMatcher("/jpos/user/login"),
                                new AntPathRequestMatcher("/jpos/user/signup")
                        ).permitAll()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("*") // 안에 해당 주소를 넣어도 됨
                        .allowedHeaders("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS", "PATCH")
                        .exposedHeaders("Authorization", "RefreshToken");
                //.allowCredentials(true); // .allowedOriginPatterns("*") 이렇게 와일드 카드로 설정하면 이거 쓰면 에러남 ( 실행 조차  X )
            }
        };
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8080"));
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

