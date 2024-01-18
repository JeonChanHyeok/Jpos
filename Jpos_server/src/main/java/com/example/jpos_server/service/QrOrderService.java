package com.example.jpos_server.service;

import com.example.jpos_server.domain.PosOrder;
import com.example.jpos_server.dto.PosOrderDto;
import com.example.jpos_server.dto.SeatDto;
import com.example.jpos_server.dto.request.PosOrderRequest;
import com.example.jpos_server.dto.response.QrOrderResponse;
import com.example.jpos_server.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class QrOrderService {
    private final SeatRepository seatRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;
    private final PosOrderRepository posOrderRepository;



    @Transactional(readOnly = true)
    public boolean correctSeat(Long storeId, Long seatId){
        if (seatRepository.existsById(seatId)){
            return seatRepository.findById(seatId).get().getStore().getId().equals(storeId);
        }else{
            return false;
        }
    }

    public QrOrderResponse makeResponse(Long storeId, Long seatId){
        QrOrderResponse qrOrderResponse = new QrOrderResponse();
        SeatDto.SeatDtoOfSeatNameAndPosUsing temp = seatRepository.findSeatNameAndPosUsingById(seatId);
        qrOrderResponse.setStoreName(storeRepository.findStoreNameById(storeId).storeName());
        qrOrderResponse.setSeatName(temp.seatName());
        qrOrderResponse.setPosUsing(temp.posUsing());
        qrOrderResponse.setMenuDtoList(menuRepository.findIdAndMenuNameAndPriceAndCategoryIdByStoreId(storeId));
        qrOrderResponse.setCategoryDtoList(categoryRepository.findIdAndCategoryNameByStoreId(storeId));
        qrOrderResponse.setPosOrderDto(posOrderRepository.findIdAndPosOrderContentAndPosOrderPriceBySeatId(seatId).orElse(new PosOrderDto.PosOrderDtoOfIdAndPosOrderContentAndPosOrderPrice(0L, "주문이 없습니다.", 0)));

        return qrOrderResponse;
    }

    public void addPosOrder(PosOrderRequest posOrderDto) {
        PosOrder posOrder = new PosOrder();
        posOrder.setPosOrderContent(posOrderDto.getPosOrderContent());
        posOrder.setStore(storeRepository.findById(posOrderDto.getStoreId()).get());
        posOrder.setPosOrderPrice(posOrderDto.getPosOrderPrice());
        posOrder.setSeat(seatRepository.findById(posOrderDto.getSeatId()).get());
        posOrderRepository.save(posOrder);
    }

    @Transactional
    public void updatePosOrder(PosOrderRequest posOrderDto) {
        PosOrder posOrder = posOrderRepository.findById(posOrderDto.getId()).get();
        posOrder.setPosOrderContent(posOrderDto.getPosOrderContent());
        posOrder.setPosOrderPrice(posOrderDto.getPosOrderPrice());
    }


}
