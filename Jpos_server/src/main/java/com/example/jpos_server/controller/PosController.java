package com.example.jpos_server.controller;

import com.example.jpos_server.service.PosService;
import com.example.jpos_server.service.SSEService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RequestMapping("/jpos/pos")
@RestController
@RequiredArgsConstructor
public class PosController {
    private final SSEService sseService;
    private final PosService posService;

    // 가게 좌석 / 주문 전체 불러오기
    @GetMapping("/{storeId}")
    public String loadSeatsAndOrderAndMenus(@PathVariable Long storeId) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(posService.makePosResponse(storeId));
    }

    @GetMapping(value = "/sub/{storeId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@PathVariable Long storeId){
        return sseService.subscribe(storeId);
    }

}
