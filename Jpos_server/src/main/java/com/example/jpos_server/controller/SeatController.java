package com.example.jpos_server.controller;

import com.example.jpos_server.domain.request.AddSeatRequest;
import com.example.jpos_server.domain.response.MenuAndOrderResponse;
import com.example.jpos_server.domain.response.SeatResponse;
import com.example.jpos_server.repository.StoreRepository;
import com.example.jpos_server.service.MenuService;
import com.example.jpos_server.service.PosOrderService;
import com.example.jpos_server.service.SeatService;
import com.example.jpos_server.service.StoreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
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

    // 가게 좌석 / 주문 전체 불러오기
    @GetMapping("/{storeId}")
    public String loadSeatsAndOrderAndMenus(@PathVariable Long storeId) throws JsonProcessingException {
        SeatResponse seatResponse = new SeatResponse();

        seatResponse.setSeatDtoList(seatService.searchSeats(storeId));
        seatResponse.setPosOrderDtoLost(posOrderService.searchPosOrderByStoreLoginId(storeId, 1));
        seatResponse.setMenuDtoList(menuService.searchMenus(storeService.searchStore(storeId)));

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(seatResponse);
    }

    @GetMapping("/order/{storeId}/{seatId}")
    public String loadMenusAndOrder(@PathVariable Long storeId, @PathVariable Long seatId) throws JsonProcessingException {
        MenuAndOrderResponse menuAndOrderResponse = seatService.searchMenusAndOrder(storeId, seatId);

        ObjectMapper objectMapper = new ObjectMapper();



        return objectMapper.writeValueAsString(menuAndOrderResponse);
    }

    @GetMapping("/setting/{storeId}")
    public String loadSeats(@PathVariable Long storeId) throws JsonProcessingException{
        SeatResponse seatResponse = new SeatResponse();

        seatResponse.setSeatDtoList(seatService.searchSeats(storeId));

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(seatResponse);
    }

    @DeleteMapping("/setting/delete/{seatId}")
    public String deleteSeat(@PathVariable Long seatId){
        seatService.deleteSeat(seatId);
        return "삭제 완료";
    }

    @PostMapping("/setting/add")
    public String addSeat(@RequestBody @Valid AddSeatRequest addSeatRequest){
        seatService.addSeat(addSeatRequest.getStoreId(), addSeatRequest.getSeatName());
        return "추가 완료";
    }
}
