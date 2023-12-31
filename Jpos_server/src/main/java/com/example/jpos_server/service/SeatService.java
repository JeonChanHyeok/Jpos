package com.example.jpos_server.service;

import com.example.jpos_server.domain.Seat;
import com.example.jpos_server.dto.PosOrderDto;
import com.example.jpos_server.dto.SeatDto;
import com.example.jpos_server.domain.response.MenuAndOrderResponse;
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
    private final MenuService menuService;
    private final CategoryService categoryService;
    private final PosOrderService posOrderService;

    @Transactional(readOnly = true)
    public List<SeatDto> searchSeats(Long storeId) {
        return seatRepository.findByStore(storeRepository.findById(storeId).get());
    }

    @Transactional(readOnly = true)
    public MenuAndOrderResponse searchMenusAndOrder(Long storeId, Long seatId) {
        MenuAndOrderResponse menuAndOrderResponse = new MenuAndOrderResponse();


        menuAndOrderResponse.setStoreName(storeRepository.findById(storeId).get().getStoreName());
        menuAndOrderResponse.setSeatName(seatRepository.findById(seatId).get().getSeatName());
        menuAndOrderResponse.setMenuDtoList(menuService.searchMenus(storeRepository.findById(storeId).get()));
        menuAndOrderResponse.setCategoryDtoList(categoryService.searchCategories(storeRepository.findById(storeId).get()));
        PosOrderDto posOrderDto = posOrderService.searchPosOrderBySeatId(seatId, 1);
        if (posOrderDto == null){
            posOrderDto = new PosOrderDto(0L, "", 0, storeId, seatId, 0);
        }
        menuAndOrderResponse.setPosOrderDto(posOrderDto);

        return menuAndOrderResponse;
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
}
