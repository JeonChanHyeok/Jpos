package com.example.jpos_server.service;

import com.example.jpos_server.domain.PosOrder;
import com.example.jpos_server.dto.PosOrderDto;
import com.example.jpos_server.repository.PosOrderRepository;
import com.example.jpos_server.repository.SeatRepository;
import com.example.jpos_server.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class PosOrderService {
    private final PosOrderRepository posOrderRepository;
    private final SeatRepository seatRepository;
    private final StoreRepository storeRepository;
    private final StoreService storeService;

    @Transactional(readOnly = true)
    public PosOrderDto searchPosOrderBySeatId(Long seatId, int state) {
        return posOrderRepository.findBySeatAndState(seatRepository.findById(seatId).get(),state);
    }
    @Transactional(readOnly = true)
    public List<PosOrderDto> searchPosOrderByStoreLoginId(Long storeId, int state) {
        return posOrderRepository.findByStoreAndState(storeService.searchStore(storeId),state);
    }


    public void addPosOrder(PosOrderDto posOrderDto) {
        PosOrder posOrder = new PosOrder(posOrderDto.posOrderContent(), posOrderDto.posOrderPrice(), storeRepository.findById(posOrderDto.storeId()).get(), seatRepository.findById(posOrderDto.seatId()).get(), posOrderDto.state());
        posOrderRepository.save(posOrder);
    }

    @Transactional
    public void updatePosOrder(PosOrderDto posOrderDto) {
        PosOrder posOrder = posOrderRepository.findById(posOrderDto.id()).get();
        posOrder.setPosOrderContent(posOrderDto.posOrderContent());
        posOrder.setPosOrderPrice(posOrderDto.posOrderPrice());
        posOrder.setState(posOrderDto.state());
    }

}
