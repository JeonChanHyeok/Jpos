package com.example.jpos_server.controller;


import com.example.jpos_server.dto.request.PosOrderRequest;
import com.example.jpos_server.service.CheckService;
import com.example.jpos_server.service.QrOrderService;
import com.example.jpos_server.service.SSEService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Check;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * QrOrder 컨트롤러
 * 손님이 자리의 Qr코드를 찍어 들어가는 주문 페이지
 */
@RequestMapping("/jpos/qrOrder")
@RestController
@RequiredArgsConstructor
public class QrOrderController {
    private final QrOrderService qrOrderService;
    private final SSEService sseService;
    private final CheckService checkService;

    /**
     * QrOrder 화면을 구성하는데 필요한 정보 반환
     * 가게 Id와 자리 Id 매칭이 안되면 SeatNotExistInStoreException 발생
     *
     * @param storeId - 가게 Id
     * @param seatId  - 자리 Id
     * @return QrOrder 화면을 구성하는데 필요한 정보 ( 가게이름, 자리이름, 카테고리목록, 메뉴목록)
     * @throws JsonProcessingException
     */
    @GetMapping("/{storeId}/{seatId}")
    public String loadMenusAndOrder(@PathVariable Long storeId, @PathVariable Long seatId) throws JsonProcessingException {
        checkService.checkCorrectSeat(storeId, seatId);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(qrOrderService.makeResponse(storeId, seatId));
    }

    /**
     * QrOrder 화면 잠금 알림을 위해 SSE구독
     * 포스기에서 특정 자리의 주문 페이지에 접근하면 그 자리의 QrOrder 화면을 잠궈야함.
     *
     * @param seatId - 자리 Id
     * @return SseEmitter - 구독 완료한 Sse
     */
    @GetMapping(value = "/sub/{seatId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@PathVariable Long seatId) {
        return sseService.subscribeForQrOrder(seatId);
    }

    /**
     * 주문 추가
     * 주문을 추가 한 후 Pos 화면에 주문이 업데이트 됐으니 주문 목록을 새로 고침 하라고 알림
     *
     * @param posOrderDto - 주문Id(0이면 새 주문), 주문내용, 주문가격, 가게Id, 자리Id
     * @return "주문 완료"
     */
    @PostMapping("/order/add")
    public String addOrder(@RequestHeader("Idempotency-Key") String idempotencyKey, @RequestBody @Valid PosOrderRequest posOrderDto) {
        if (posOrderDto.getId() == 0) {
            qrOrderService.addPosOrder(idempotencyKey, posOrderDto);
        } else {
            qrOrderService.updatePosOrder(posOrderDto);
        }
        sseService.notifyForPos(posOrderDto.getStoreId(), "");
        return "주문 완료";
    }
}
