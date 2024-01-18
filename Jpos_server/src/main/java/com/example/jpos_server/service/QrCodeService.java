package com.example.jpos_server.service;

import com.example.jpos_server.dto.SeatDto;
import com.example.jpos_server.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class QrCodeService {
    private final SeatRepository seatRepository;

    public List<SeatDto.SeatDtoOfIdAndSeatName> makeResponse(Long storeId){
        return seatRepository.findIdAndSeatNameByStoreId(storeId);
    }
}
