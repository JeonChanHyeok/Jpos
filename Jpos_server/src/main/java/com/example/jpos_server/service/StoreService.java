package com.example.jpos_server.service;


import com.example.jpos_server.domain.Store;
import com.example.jpos_server.repository.PosEmitterRepository;
import com.example.jpos_server.repository.StoreRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RequiredArgsConstructor
@Transactional
@Service
public class StoreService  {

    private final StoreRepository storeRepository;

    @Transactional(readOnly = true)
    public Store searchStore(Long storeId){
        return storeRepository.findById(storeId).get();
    }


}
