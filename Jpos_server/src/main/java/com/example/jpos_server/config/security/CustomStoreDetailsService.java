package com.example.jpos_server.config.security;

import com.example.jpos_server.domain.Store;
import com.example.jpos_server.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("storeDetailsService")
@RequiredArgsConstructor
public class CustomStoreDetailsService implements UserDetailsService {

    private final StoreRepository storeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Store store = storeRepository.findByStoreLoginId(username);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(store == null){
            throw new UsernameNotFoundException("no account");
        }
        //권한 설정 나중에 업주, 직원 등으로 나눌 예정
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("USER"));

        StoreContext storeContext = new StoreContext(store, roles);
        return storeContext;
    }
}
