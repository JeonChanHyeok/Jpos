package com.example.jpos_server.config.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomStoreDetailsService storeDetailsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String storeLoginId = authentication.getName();
        String storeLoginPw = (String) authentication.getCredentials();
        StoreContext storeContext = (StoreContext) storeDetailsService.loadUserByUsername(storeLoginId);
        if (!bCryptPasswordEncoder.matches(storeLoginPw, storeContext.getStore().getStoreLoginPw())){
            throw new BadCredentialsException("PwError");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(storeContext.getStore(), null, storeContext.getAuthorities());

        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
