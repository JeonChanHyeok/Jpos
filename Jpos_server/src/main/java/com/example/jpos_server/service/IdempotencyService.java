package com.example.jpos_server.service;

import com.example.jpos_server.config.error.ErrorCode;
import com.example.jpos_server.config.error.exceptions.DuplicatedRequestException;
import com.example.jpos_server.domain.IdempotencyKey;
import com.example.jpos_server.repository.IdempotencyKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class IdempotencyService {
    private final IdempotencyKeyRepository idempotencyKeyRepository;

    @Transactional(readOnly = true)
    public void checkDuplicatedRequest(String key){
        if(idempotencyKeyRepository.existsById(key)){
            throw new DuplicatedRequestException("중복된 요청", ErrorCode.DUPLICATED_REQUEST);
        }else{
            IdempotencyKey newKey = new IdempotencyKey();
            newKey.setId(key);
            idempotencyKeyRepository.save(newKey);
        }
    }

    @Transactional
    @Async
    @Scheduled(cron = "0 0 0 * * *")
    public void resetIdempotencyKeys(){
        idempotencyKeyRepository.deleteAll();
    }
}
