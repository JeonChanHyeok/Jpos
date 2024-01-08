package com.example.jpos_server.service;


import com.example.jpos_server.domain.Store;
import com.example.jpos_server.dto.StoreDto;
import com.example.jpos_server.repository.StoreRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class StoreService  {

    private final StoreRepository storeRepository;

    public boolean makeStore(StoreDto storeDto) {
        storeRepository.save(new Store(storeDto.storeName(), storeDto.latitude(), storeDto.longitude()));
        return true;
    }


    @Transactional(readOnly = true)
    public Store searchStore(Long storeId){
        return storeRepository.findById(storeId).get();
    }


}
