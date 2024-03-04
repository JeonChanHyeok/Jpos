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

/**
 * Inorder 화면 컨트롤러
 * 포스기의 주문을 조작한다.
 */
@RequestMapping("/jpos/inOrder")
@RestController
@RequiredArgsConstructor
public class InOrderController {
    private final InOrderService inOrderService;
    private final SSEService sseService;


    /**
     * 포스기 주문 수정 화면에 필요한 데이터 반환
     * 포스기에서 주문을 수정 중엔 Qr 주문을 비활성화한 후 비활성화 한 사실을 Qr 주문 화면에 SSE를 통해 알림.
     * 가게와 자리 Id 매칭이 안될 경우 예외 발생 예정
     *
     * @param storeId - 가게 Id
     * @param seatId - 자리 Id
     * @return 가게의 모든 메뉴 정보, 선택한 자리 정보
     * @throws JsonProcessingException - writeValueAsString 사용에 필요
     */
    @GetMapping("/{storeId}/{seatId}")
    public String loadAllMenusAndCategories(@PathVariable Long storeId, @PathVariable Long seatId) throws JsonProcessingException {
        inOrderService.setPosUsing(seatId, 1);
        sseService.notifyForQrOrder(seatId,"");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(inOrderService.makeResponse(storeId, seatId));
    }

    /**
     * 주문 수정이 종료됐을 때 주문 수정이 종료됐음을 Qr 주문 화면에 알림.
     * 포스기 이용자의 주문 수정 내용 관계 없이 주문 수정 화면을 나오면 실행됨.
     *
     * @param seatId - 자리 Id
     */
    @PatchMapping("/{seatId}")
    public void setPosUnUsing(@PathVariable Long seatId){
        inOrderService.setPosUsing(seatId, 0);
        sseService.notifyForQrOrder(seatId,"");
    }

    /**
     * 주문 수정 및 추가
     * Post와 Put으로 나눠야 할지 고민중이다.
     * 클라이언트에서 새 주문일 경우 id에 0을 채워 보낸다.
     * 주문 id가 0인 주문 정보가 입력되면 새 주문을 등록한다.
     * 주문 id가 0이 아닌 주문 정보가 들어오면 id로 주문을 찾아 수정한다.
     *
     * @param posOrderDto - 주문 Id, 주문 내용, 주문 가격, 주문 가게 Id, 주문 자리 Id
     */
    @PostMapping("/order/add")
    public void addOrder(@RequestBody @Valid PosOrderRequest posOrderDto){
        if(posOrderDto.getId() == 0){
            inOrderService.addPosOrder(posOrderDto);
        }else{
            inOrderService.updatePosOrder(posOrderDto);
        }
    }

    /**
     * 결제 완료
     * 아직 결제를 구현하지 않았기에 바로 결제를 완료한다.
     * 결제가 완료되면 주문 내용을 바탕으로 완료된 주문을 만들어 저장 한 후 주문을 삭제한다.
     *
     * @param posOrderDto - 주문 Id, 주문 내용, 주문 가격, 주문 가게 Id, 주문 자리 Id
     */
    @PostMapping("/order/end")
    public void endOrder(@RequestBody @Valid PosOrderRequest posOrderDto){
        inOrderService.endOrder(posOrderDto);
    }

    /**
     * 주문 취소
     * 주문 Id를 입력받아 주문을 삭제한다.
     *
     * @param orderId - 주문 Id
     */
    @DeleteMapping("/order/delete/{orderId}")
    public void deleteOrder(@PathVariable Long orderId){
        inOrderService.deleteOrder(orderId);
    }

}
