package com.example.jpos_server.controller;

import com.example.jpos_server.dto.request.NewSeatRequest;
import com.example.jpos_server.service.CheckService;
import com.example.jpos_server.service.IdempotencyService;
import com.example.jpos_server.service.SeatSettingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * SeatSetting 컨트롤러
 * 자리정보 수정
 */
@RequestMapping("/jpos/seatSetting")
@RestController
@RequiredArgsConstructor
public class SeatSettingController {
    private final SeatSettingService seatSettingService;
    private final CheckService checkService;
    private final IdempotencyService idempotencyService;

    /**
     * 가게의 자리 정보 반환
     *
     * @param storeId - 가게 Id
     * @return 자리 정보 - 자리Id, 자리이름
     * @throws JsonProcessingException
     */
    @GetMapping("/{storeId}")
    public String loadSeats(@RequestHeader("Authorization") String token, @PathVariable Long storeId) throws JsonProcessingException {
        checkService.checkValidUserForRequest(token, storeId, 0);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(seatSettingService.makeResponse(storeId));
    }

    /**
     * 자리 추가
     *
     * @param addSeatRequest - 가게Id, 자리이름
     * @return - "추가 완료"
     */
    @PostMapping("/add")
    public String addSeat(@RequestHeader("Authorization") String token, @RequestHeader("Idempotency-Key") String idempotencyKey, @RequestBody @Valid NewSeatRequest addSeatRequest) {
        idempotencyService.checkDuplicatedRequest(idempotencyKey);
        checkService.checkValidUserForRequest(token, addSeatRequest.getStoreId(), 0);
        seatSettingService.addSeat(addSeatRequest.getStoreId(), addSeatRequest.getSeatName());
        return "추가 완료";
    }

    /**
     * 자리 삭제
     *
     * @param seatId - 자리Id
     * @return - "삭제 완료"
     */
    @DeleteMapping("/delete/{seatId}")
    public String deleteSeat(@RequestHeader("Authorization") String token, @PathVariable Long seatId) {
        checkService.checkValidUserForRequest(token, seatId, 1);
        seatSettingService.deleteSeat(seatId);
        return "삭제 완료";
    }


}
