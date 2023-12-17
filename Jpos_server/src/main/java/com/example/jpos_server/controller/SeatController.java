package com.example.jpos_server.controller;

import com.example.jpos_server.dto.resp.MenuAndOrderResponse;
import com.example.jpos_server.dto.resp.SeatResponse;
import com.example.jpos_server.repository.StoreRepository;
import com.example.jpos_server.service.MenuService;
import com.example.jpos_server.service.PosOrderService;
import com.example.jpos_server.service.SeatService;
import com.example.jpos_server.service.StoreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/jpos/seat")
@RestController
@RequiredArgsConstructor
public class SeatController {
    private final StoreRepository storeRepository;
    private final SeatService seatService;
    private final PosOrderService posOrderService;
    private final MenuService menuService;
    private final StoreService storeService;


    @GetMapping("/{storeLoginId}")
    public String loadSeats(@PathVariable String storeLoginId) throws JsonProcessingException {
        SeatResponse seatResponse = new SeatResponse();

        seatResponse.setSeatDtoList(seatService.searchSeats(storeLoginId));
        seatResponse.setPosOrderDtoLost(posOrderService.searchPosOrderByStoreLoginId(storeLoginId, 1));
        seatResponse.setMenuDtoList(menuService.searchMenus(storeService.searchStore(storeLoginId)));

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(seatResponse);
    }

    @GetMapping("/order/{storeId}/{seatId}")
    public String loadMenusAndOrder(@PathVariable Long storeId, @PathVariable Long seatId) throws JsonProcessingException {
        MenuAndOrderResponse menuAndOrderResponse = seatService.searchMenusAndOrder(storeId, seatId);

        ObjectMapper objectMapper = new ObjectMapper();



        return objectMapper.writeValueAsString(menuAndOrderResponse);
    }
}
