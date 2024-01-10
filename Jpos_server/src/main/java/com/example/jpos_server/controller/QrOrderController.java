package com.example.jpos_server.controller;


import com.example.jpos_server.domain.response.MenuAndOrderResponse;
import com.example.jpos_server.dto.PosOrderDto;
import com.example.jpos_server.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/jpos/qrOrder")
@RestController
@RequiredArgsConstructor
public class QrOrderController {
    private final SeatService seatService;
    private final MenuService menuService;
    private final CategoryService categoryService;
    private final PosOrderService posOrderService;
    private final StoreService storeService;
    private final SimpMessageSendingOperations sendingOperations;

    @GetMapping("/{storeId}/{seatId}")
    public String loadMenusAndOrder(@PathVariable Long storeId, @PathVariable Long seatId) throws JsonProcessingException {

        if(seatService.correctSeat(storeId, seatId)){
            MenuAndOrderResponse menuAndOrderResponse = new MenuAndOrderResponse();
            menuAndOrderResponse.setStoreName(storeService.searchStore(storeId).getStoreName());
            menuAndOrderResponse.setSeatName(seatService.searchSeat(seatId).seatName());
            menuAndOrderResponse.setPosUsing(seatService.searchSeat(seatId).posUsing());
            menuAndOrderResponse.setMenuDtoList(menuService.searchMenus(storeService.searchStore(storeId)));
            menuAndOrderResponse.setCategoryDtoList(categoryService.searchCategories(storeService.searchStore(storeId)));
            PosOrderDto posOrderDto = posOrderService.searchPosOrderBySeatId(seatId);
            menuAndOrderResponse.setPosOrderDto(posOrderDto);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            return objectMapper.writeValueAsString(menuAndOrderResponse);
        }else{
            return "잘못된 접근입니다.";
        }
    }

    @PostMapping("/order/add")
    public String addOrder(@RequestBody @Valid PosOrderDto posOrderDto){
        if(posOrderDto.id() == 0){
            posOrderService.addPosOrder(posOrderDto);
        }else{
            posOrderService.updatePosOrder(posOrderDto);
        }
        sendingOperations.convertAndSend("/send/"+posOrderDto.storeId(),"yeah");
        return "주문 완료";
    }
}
