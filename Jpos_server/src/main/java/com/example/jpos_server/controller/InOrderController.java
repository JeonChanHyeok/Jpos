package com.example.jpos_server.controller;


import com.example.jpos_server.dto.request.PosOrderRequest;
import com.example.jpos_server.service.InOrderService;
import com.example.jpos_server.service.SSEService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


//1. qrorder에 이미 들어와 있는 상태에서 inorder 접속시
//        qrorder 페이지에 소켓 열어서 stomp로 inorder페이지 들어오면 감지해서 카운터에서 주문 조정중입니다. 모달띄우기
//
//2. inorder 접속상태에서 qrorder 접근 시도
//inorder 접속시 router path 변경 (noqrorder 페이지 : qrorder페이지 복붙 + 이미 모달창 켜진상태 + stomp 이용해서 inorder 페이지 벗어날시 router path 정상화 + 새로고침)

@RequestMapping("/jpos/inOrder")
@RestController
@RequiredArgsConstructor
public class InOrderController {
    private final InOrderService inOrderService;
    private final SSEService sseService;


    @GetMapping("/{storeId}/{seatId}")
    public String loadAllMenusAndCategories(@PathVariable Long storeId, @PathVariable Long seatId) throws JsonProcessingException {
        inOrderService.setPosUsing(seatId, 1);
        sseService.notify2(seatId,"");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(inOrderService.makeResponse(storeId, seatId));
    }

    @PatchMapping("/{seatId}")
    public void setPosUnUsing(@PathVariable Long seatId){
        inOrderService.setPosUsing(seatId, 0);
        sseService.notify2(seatId,"");
    }

    @PostMapping("/order/add")
    public String addOrder(@RequestBody @Valid PosOrderRequest posOrderDto){
        if(posOrderDto.getId() == 0){
            inOrderService.addPosOrder(posOrderDto);
        }else{
            inOrderService.updatePosOrder(posOrderDto);
        }
        return "주문 완료";
    }

    @PostMapping("/order/end")
    public String endOrder(@RequestBody @Valid PosOrderRequest posOrderDto){
        //작성예정
        return "계산 완료";
    }

}
