package com.example.jpos_server.service;


import com.example.jpos_server.domain.Store;
import com.example.jpos_server.repository.StoreEmitterRepository;
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

    // 기본 타임아웃 설정
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
    private final StoreEmitterRepository storeEmitterRepository;
    private final StoreRepository storeRepository;

    public SseEmitter subscribe(Long storeId){
        SseEmitter emitter = createEmitter(storeId);

        sendToClient(storeId, "EventStream Created. [userId=" + storeId + "]");
        return emitter;
    }

    public void notify(Long storeId, Object event) {
        sendToClient(storeId, event);
    }

    private void sendToClient(Long storeId, Object data) {
        SseEmitter emitter = storeEmitterRepository.get(storeId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().id(String.valueOf(storeId)).name("pos").data(data));
            } catch (IOException exception) {
                storeEmitterRepository.deleteById(storeId);
                emitter.completeWithError(exception);
            }
        }
    }

    private SseEmitter createEmitter(Long storeId) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        storeEmitterRepository.save(storeId, emitter);

        // Emitter가 완료될 때(모든 데이터가 성공적으로 전송된 상태) Emitter를 삭제한다.
        emitter.onCompletion(() -> storeEmitterRepository.deleteById(storeId));
        // Emitter가 타임아웃 되었을 때(지정된 시간동안 어떠한 이벤트도 전송되지 않았을 때) Emitter를 삭제한다.
        emitter.onTimeout(() -> storeEmitterRepository.deleteById(storeId));

        return emitter;
    }


    @Transactional(readOnly = true)
    public Store searchStore(Long storeId){
        return storeRepository.findById(storeId).get();
    }


}
