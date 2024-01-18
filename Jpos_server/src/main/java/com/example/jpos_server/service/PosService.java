package com.example.jpos_server.service;

import com.example.jpos_server.dto.response.PosResponse;
import com.example.jpos_server.repository.MenuRepository;
import com.example.jpos_server.repository.PosOrderRepository;
import com.example.jpos_server.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class PosService {
    private final PosOrderRepository posOrderRepository;
    private final SeatRepository seatRepository;
    private final MenuRepository menuRepository;


    @Transactional(readOnly = true)
    public PosResponse makePosResponse(Long storeId){
        PosResponse posResponse = new PosResponse();
        posResponse.setPosOrderResponseList(posOrderRepository.findSeatIdAndPosOrderPriceAndPosOrPosOrderContentAndCreatedDateByStoreId(storeId));
        posResponse.setSeatResponseList(seatRepository.findIdAndSeatNameByStoreId(storeId));
        posResponse.setMenuResponseList(menuRepository.findIdAndMenuNameAndPriceByStoreId(storeId));
        return posResponse;
    }
}
