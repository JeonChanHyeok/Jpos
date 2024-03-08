package com.example.jpos_server.service;

import com.example.jpos_server.domain.EndPosOrder;
import com.example.jpos_server.domain.PosOrder;
import com.example.jpos_server.domain.Seat;
import com.example.jpos_server.dto.PosOrderDto;
import com.example.jpos_server.dto.request.PosOrderRequest;
import com.example.jpos_server.dto.response.InOrderResponse;
import com.example.jpos_server.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class InOrderService {
    private final SeatRepository seatRepository;
    private final PosOrderRepository posOrderRepository;
    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;
    private final StoreRepository storeRepository;
    private final EndPosOrderRepository endPosOrderRepository;

    @Transactional
    public void setPosUsing(Long seatId, int posUsing) {
        Seat seat = seatRepository.findById(seatId).get();
        seat.setPosUsing(posUsing);
    }

    @Transactional(readOnly = true)
    public InOrderResponse makeResponse(Long storeId, Long SeatId) {
        InOrderResponse inOrderResponse = new InOrderResponse();
        inOrderResponse.setPosOrderDto(posOrderRepository.findIdAndPosOrderContentAndPosOrderPriceBySeatId(SeatId).orElse(new PosOrderDto.PosOrderDtoOfIdAndPosOrderContentAndPosOrderPrice(0L, "주문이 없습니다.", 0)));
        inOrderResponse.setMenuDtoList(menuRepository.findIdAndMenuNameAndPriceAndCategoryIdByStoreId(storeId));
        inOrderResponse.setCategoryDtoList(categoryRepository.findIdAndCategoryNameByStoreId(storeId));
        return inOrderResponse;
    }

    @Transactional
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

    @Transactional
    public void endOrder(PosOrderRequest posOrderDto) {

        EndPosOrder endPosOrder = new EndPosOrder();
        endPosOrder.setStore(storeRepository.findById(posOrderDto.getStoreId()).get());
        endPosOrder.setSeatName(seatRepository.findById(posOrderDto.getSeatId()).get().getSeatName());
        endPosOrder.setPosOrderPrice(posOrderDto.getPosOrderPrice());
        endPosOrder.setPosOrderContent(posOrderDto.getPosOrderContent().replace("<br/>", ", "));
        endPosOrderRepository.save(endPosOrder);
        if (posOrderDto.getId() != 0) {
            posOrderRepository.deleteById(posOrderDto.getId());
        }
    }

    @Transactional
    public void deleteOrder(Long orderId) {
        posOrderRepository.deleteById(orderId);
    }


}
