package com.example.jpos_server.service;

import com.example.jpos_server.repository.PosEmitterRepository;
import com.example.jpos_server.repository.PosUsingEmitterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RequiredArgsConstructor
@Transactional
@Service
public class SSEService {
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
    private final PosUsingEmitterRepository posUsingEmitterRepository;
    private final PosEmitterRepository posEmitterRepository;

    public SseEmitter subscribe(Long storeId){
        SseEmitter emitter = createEmitter(storeId);

        sendToClient(storeId, "EventStream Created. [userId=" + storeId + "]", 0);
        return emitter;
    }

    public void notify(Long storeId, Object event) {
        sendToClient(storeId, event, 1);
    }

    private void sendToClient(Long storeId, Object data, int isStart) {
        SseEmitter emitter = posEmitterRepository.get(storeId);
        if (emitter != null) {
            try {
                if(isStart == 0) emitter.send(SseEmitter.event().id(String.valueOf(storeId)).name("posStart").data(data));
                else emitter.send(SseEmitter.event().id(String.valueOf(storeId)).name("pos").data(data));
            } catch (IOException exception) {
                posEmitterRepository.deleteById(storeId);
                emitter.completeWithError(exception);
            }
        }
    }

    private SseEmitter createEmitter(Long storeId) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        posEmitterRepository.save(storeId, emitter);

        // Emitter가 완료될 때(모든 데이터가 성공적으로 전송된 상태) Emitter를 삭제한다.
        emitter.onCompletion(() -> posEmitterRepository.deleteById(storeId));
        // Emitter가 타임아웃 되었을 때(지정된 시간동안 어떠한 이벤트도 전송되지 않았을 때) Emitter를 삭제한다.
        emitter.onTimeout(() -> posEmitterRepository.deleteById(storeId));

        return emitter;
    }




    public SseEmitter subscribe2(Long seatId){
        SseEmitter emitter = createEmitter(seatId);

        sendToClient(seatId, "EventStream Created. [userId=" + seatId + "]", 0);
        return emitter;
    }

    public void notify2(Long seatId, Object event) {
        sendToClient(seatId, event, 1);
    }

    private void sendToClient2(Long seatId, Object data, int isStart) {
        SseEmitter emitter = posUsingEmitterRepository.get(seatId);
        if (emitter != null) {
            try {
                if(isStart == 0) emitter.send(SseEmitter.event().id(String.valueOf(seatId)).name("qrOrderStart").data(data));
                else emitter.send(SseEmitter.event().id(String.valueOf(seatId)).name("qrOrder").data(data));
            } catch (IOException exception) {
                posUsingEmitterRepository.deleteById(seatId);
                emitter.completeWithError(exception);
            }
        }
    }

    private SseEmitter createEmitter2(Long seatId) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        posUsingEmitterRepository.save(seatId, emitter);

        // Emitter가 완료될 때(모든 데이터가 성공적으로 전송된 상태) Emitter를 삭제한다.
        emitter.onCompletion(() -> posUsingEmitterRepository.deleteById(seatId));
        // Emitter가 타임아웃 되었을 때(지정된 시간동안 어떠한 이벤트도 전송되지 않았을 때) Emitter를 삭제한다.
        emitter.onTimeout(() -> posUsingEmitterRepository.deleteById(seatId));

        return emitter;
    }

}
