package com.example.jpos_server.controller;

import com.example.jpos_server.domain.response.SeatResponse;
import com.example.jpos_server.service.MenuService;
import com.example.jpos_server.service.PosOrderService;
import com.example.jpos_server.service.SeatService;
import com.example.jpos_server.service.StoreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/jpos/pos")
@RestController
@RequiredArgsConstructor
public class PosController {
    private final SeatService seatService;
    private final PosOrderService posOrderService;
    private final MenuService menuService;
    private final StoreService storeService;

    // 가게 좌석 / 주문 전체 불러오기
    @GetMapping("/{storeId}")
    public String loadSeatsAndOrderAndMenus(@PathVariable Long storeId) throws JsonProcessingException {
        SeatResponse seatResponse = new SeatResponse();

        seatResponse.setSeatDtoList(seatService.searchSeats(storeId));
        seatResponse.setPosOrderDtoLost(posOrderService.searchPosOrderByStoreLoginId(storeId));
        seatResponse.setMenuDtoList(menuService.searchMenus(storeService.searchStore(storeId)));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(seatResponse);
    }
}
