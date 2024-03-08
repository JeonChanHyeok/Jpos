package com.example.jpos_server.controller;

import com.example.jpos_server.service.CheckService;
import com.example.jpos_server.service.QrCodeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * QrCode컨트롤러
 * Qr코드 생성 페이지
 */
@RequestMapping("/jpos/qrCode")
@RestController
@RequiredArgsConstructor
public class QrCodeController {
    private final QrCodeService qrCodeService;
    private final CheckService checkService;

    /**
     * Qr코드 생성을 위해 자리 정보를 반환
     *
     * @param storeLoginId - 가게 Id
     * @return 자리정보리스트(자리Id, 자리이름)
     * @throws JsonProcessingException - writeValueAsString사용
     */
    @GetMapping("/{storeLoginId}")
    public String loadSeatsForQr(@RequestHeader("Authorization") String token, @PathVariable Long storeLoginId) throws JsonProcessingException {
        checkService.checkValidUserForRequest(token, storeLoginId, 0);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(qrCodeService.makeResponse(storeLoginId));
    }
}
