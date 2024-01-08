package com.example.jpos_server.controller;


import com.example.jpos_server.domain.response.MenuAndOrderResponse;
import com.example.jpos_server.dto.PosOrderDto;
import com.example.jpos_server.service.MenuService;
import com.example.jpos_server.service.PosOrderService;
import com.example.jpos_server.service.SeatService;
import com.example.jpos_server.service.StoreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/jpos/qrOrder")
@RestController
@RequiredArgsConstructor
public class QrOrderController {
    private final SeatService seatService;
    private final PosOrderService posOrderService;
    private final StoreService storeService;
    private final SimpMessageSendingOperations sendingOperations;

    @GetMapping("/{storeId}/{seatId}")
    public String loadMenusAndOrder(@PathVariable Long storeId, @PathVariable Long seatId) throws JsonProcessingException {
        MenuAndOrderResponse menuAndOrderResponse = seatService.searchMenusAndOrder(storeId, seatId);

        ObjectMapper objectMapper = new ObjectMapper();



        return objectMapper.writeValueAsString(menuAndOrderResponse);
    }

    @PostMapping("/order/add")
    @ResponseBody
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
