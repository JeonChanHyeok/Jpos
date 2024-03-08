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
public class SeatSettingService {
    private final SeatRepository seatRepository;
    private final StoreRepository storeRepository;


    public List<SeatDto.SeatDtoOfIdAndSeatName> makeResponse(Long storeId) {
        return seatRepository.findIdAndSeatNameByStoreId(storeId);
    }

    public void addSeat(Long storeId, String seatName) {
        Seat seat = new Seat();
        seat.setStore(storeRepository.findById(storeId).get());
        seat.setSeatName(seatName);
        seatRepository.save(seat);
    }

    public void deleteSeat(Long seatId) {
        seatRepository.deleteById(seatId);
    }

}
