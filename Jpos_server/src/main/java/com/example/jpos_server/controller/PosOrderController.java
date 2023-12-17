package com.example.jpos_server.controller;

import com.example.jpos_server.dto.PosOrderDto;
import com.example.jpos_server.repository.StoreRepository;
import com.example.jpos_server.service.PosOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/jpos/order")
@RestController
@RequiredArgsConstructor
public class PosOrderController {
    private final PosOrderService posOrderService;
    private final StoreRepository storeRepository;
    private final SimpMessageSendingOperations sendingOperations;

    @PostMapping("/qr")
    @ResponseBody
    public String addOrder(PosOrderDto posOrderDto){
        if(posOrderDto.id() == 0){
            posOrderService.addPosOrder(posOrderDto);
        }else{
            posOrderService.updatePosOrder(posOrderDto);
        }
        sendingOperations.convertAndSend("/send/"+storeRepository.findById(posOrderDto.storeId()).get().getStoreLoginId(),"yeah");
        return "주문 완료";
    }
}
