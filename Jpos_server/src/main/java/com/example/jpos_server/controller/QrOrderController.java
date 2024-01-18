package com.example.jpos_server.controller;


import com.example.jpos_server.dto.request.PosOrderRequest;
import com.example.jpos_server.service.QrOrderService;
import com.example.jpos_server.service.SSEService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RequestMapping("/jpos/qrOrder")
@RestController
@RequiredArgsConstructor
public class QrOrderController {
    private final QrOrderService qrOrderService;
    private final SSEService sseService;

    @GetMapping("/{storeId}/{seatId}")
    public String loadMenusAndOrder(@PathVariable Long storeId, @PathVariable Long seatId) throws JsonProcessingException {

        if(qrOrderService.correctSeat(storeId, seatId)){
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(qrOrderService.makeResponse(storeId, seatId));
        }else{
            return "잘못된 접근입니다.";
        }
    }

    @GetMapping(value = "/sub/{seatId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@PathVariable Long seatId){
        return sseService.subscribe2(seatId);
    }


    @PostMapping("/order/add")
    public String addOrder(@RequestBody @Valid PosOrderRequest posOrderDto){
        if(posOrderDto.getId() == 0){
            qrOrderService.addPosOrder(posOrderDto);
        }else{
            qrOrderService.updatePosOrder(posOrderDto);
        }
        sseService.notify(posOrderDto.getStoreId(), "");
        return "주문 완료";
    }
}
