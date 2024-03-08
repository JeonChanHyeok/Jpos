package com.example.jpos_server.controller;

import com.example.jpos_server.service.CheckService;
import com.example.jpos_server.service.PosService;
import com.example.jpos_server.service.SSEService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * pos 컨트롤러
 * 현재 등록된 주문을 한눈에 볼 수 있음.
 */
@RequestMapping("/jpos/pos")
@RestController
@RequiredArgsConstructor
public class PosController {
    private final SSEService sseService;
    private final PosService posService;
    private final CheckService checkService;

    /**
     * pos 화면을 구성할 정보 반환
     *
     * @param storeId - 가게 Id
     * @return 가게의 모든 자리 정보,메뉴 정보 및 현재 등록된 주문 정보
     * @throws JsonProcessingException - writeValueAsString 사용 위한 throws
     */
    @GetMapping("/{storeId}")
    public String loadSeatsAndOrderAndMenus(@RequestHeader("Authorization") String token, @PathVariable Long storeId) throws JsonProcessingException {
        checkService.checkValidUserForRequest(token, storeId, 0);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(posService.makePosResponse(storeId));
    }

    /**
     * SSE 구독
     * 구독한 클라이언트는 해당 가게의 주문정보의 변경이 있을 시 자동으로 새로고침 하여 실시간으로 pos 화면을 업데이트한다.
     *
     * @param storeId - 가게 Id
     * @return SseEmitter
     */
    @GetMapping(value = "/sub/{storeId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@PathVariable Long storeId){
        return sseService.subscribeForPos(storeId);
    }

}
