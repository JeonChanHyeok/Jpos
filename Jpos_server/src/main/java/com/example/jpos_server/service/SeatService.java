package com.example.jpos_server.service;

import com.example.jpos_server.domain.Seat;
import com.example.jpos_server.dto.SeatDto;
import com.example.jpos_server.repository.SeatEmitterRepository;
import com.example.jpos_server.repository.SeatRepository;
import com.example.jpos_server.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class SeatService {
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
    private final SeatRepository seatRepository;
    private final StoreRepository storeRepository;
    private final SeatEmitterRepository seatEmitterRepository;

    @Transactional(readOnly = true)
    public boolean correctSeat(Long storeId, Long seatId){
        if (seatRepository.existsById(seatId)){
            return seatRepository.findById(seatId).get().getStore().getId().equals(storeId);
        }else{
            return false;
        }
    }

    @Transactional(readOnly = true)
    public SeatDto searchSeat(Long seatId){
        return SeatDto.from(seatRepository.findById(seatId).get());
    }

    @Transactional(readOnly = true)
    public List<SeatDto> searchSeats(Long storeId) {
        return seatRepository.findByStore(storeRepository.findById(storeId).get());
    }

    public void deleteSeat(Long seatId){
        seatRepository.deleteById(seatId);
    }

    public void addSeat(Long storeId, String seatName){
        Seat seat = new Seat();
        seat.setStore(storeRepository.findById(storeId).get());
        seat.setSeatName(seatName);
        seatRepository.save(seat);
    }

    @Transactional
    public void setPosUsing(Long seatId, int posUsing){
        Seat seat = seatRepository.findById(seatId).get();
        seat.setPosUsing(posUsing);
    }

    public SseEmitter subscribe(Long seatId){
        SseEmitter emitter = createEmitter(seatId);

        sendToClient(seatId, "EventStream Created. [userId=" + seatId + "]");
        return emitter;
    }

    public void notify(Long seatId, Object event) {
        sendToClient(seatId, event);
    }

    private void sendToClient(Long seatId, Object data) {
        SseEmitter emitter = seatEmitterRepository.get(seatId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().id(String.valueOf(seatId)).name("qrOrder").data(data));
            } catch (IOException exception) {
                seatEmitterRepository.deleteById(seatId);
                emitter.completeWithError(exception);
            }
        }
    }

    private SseEmitter createEmitter(Long seatId) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        seatEmitterRepository.save(seatId, emitter);

        // Emitter가 완료될 때(모든 데이터가 성공적으로 전송된 상태) Emitter를 삭제한다.
        emitter.onCompletion(() -> seatEmitterRepository.deleteById(seatId));
        // Emitter가 타임아웃 되었을 때(지정된 시간동안 어떠한 이벤트도 전송되지 않았을 때) Emitter를 삭제한다.
        emitter.onTimeout(() -> seatEmitterRepository.deleteById(seatId));

        return emitter;
    }
}
