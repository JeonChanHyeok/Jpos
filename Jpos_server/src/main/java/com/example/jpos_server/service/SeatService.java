package com.example.jpos_server.service;

import com.example.jpos_server.dto.PosOrderDto;
import com.example.jpos_server.dto.SeatDto;
import com.example.jpos_server.dto.resp.MenuAndOrderResponse;
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
    public List<SeatDto> searchSeats(String storeLoginId) {
        return seatRepository.findByStore(storeRepository.findByStoreLoginId(storeLoginId));
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
}
