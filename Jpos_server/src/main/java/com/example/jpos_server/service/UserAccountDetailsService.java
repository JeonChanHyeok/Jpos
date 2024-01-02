package com.example.jpos_server.service;

import com.example.jpos_server.config.security.UserAccountDetails;
import com.example.jpos_server.domain.User.UserAccount;
import com.example.jpos_server.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserAccountDetailsService implements UserDetailsService {

    @Autowired
    UserAccountRepository userAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountRepository.findByLoginId(loginId)
                .orElseThrow(() ->
                        new UsernameNotFoundException("아이디없음"));
        return UserAccountDetails.build(userAccount);
    }
}
