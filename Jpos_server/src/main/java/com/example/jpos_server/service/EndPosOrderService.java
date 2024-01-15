package com.example.jpos_server.service;

import com.example.jpos_server.domain.EndPosOrder;
import com.example.jpos_server.dto.PosOrderDto;
import com.example.jpos_server.repository.EndPosOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class EndPosOrderService {
    private final EndPosOrderRepository endPosOrderRepository;
    private final StoreService storeService;

    public void makeEndPosOrderByPosOrder(PosOrderDto posOrderDto){
        EndPosOrder endPosOrder = new EndPosOrder();
        endPosOrder.setStore(storeService.searchStore(posOrderDto.storeId()));
    }
}
