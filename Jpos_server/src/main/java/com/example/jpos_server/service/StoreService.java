package com.example.jpos_server.service;

import com.example.jpos_server.config.security.DuplicateStoreLoginIdException;
import com.example.jpos_server.config.security.StoreContext;
import com.example.jpos_server.domain.Store;
import com.example.jpos_server.dto.StoreDto;
import com.example.jpos_server.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class StoreService  {

    private final StoreRepository storeRepository;

    public boolean makeStore(StoreDto storeDto) {
        checkDup(storeDto.storeLoginId());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        storeRepository.save(new Store(storeDto.storeName(), storeDto.latitude(), storeDto.longitude(), storeDto.storeLoginId(), passwordEncoder.encode(storeDto.storeLoginPw())));
        return true;

    }

    private void checkDup(String storeLoginId) {
        if (storeRepository.existsByStoreLoginId(storeLoginId)) {
            throw new DuplicateStoreLoginIdException();
        }
    }

    @Transactional(readOnly = true)
    public Store searchStore(String storeLoginId){
        return storeRepository.findByStoreLoginId(storeLoginId);
    }


}
