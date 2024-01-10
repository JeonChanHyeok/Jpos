package com.example.jpos_server.service;

import com.example.jpos_server.domain.Seat;
import com.example.jpos_server.dto.SeatDto;
import com.example.jpos_server.repository.SeatRepository;
import com.example.jpos_server.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class SeatService {
    private final SeatRepository seatRepository;
    private final StoreRepository storeRepository;

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
}
