package com.example.jpos_server.controller;

import com.example.jpos_server.dto.request.NewSeatRequest;
import com.example.jpos_server.service.SeatSettingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/jpos/seatSetting")
@RestController
@RequiredArgsConstructor
public class SeatSettingController {
    private final SeatSettingService seatSettingService;

    @GetMapping("/{storeId}")
    public String loadSeats(@PathVariable Long storeId) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(seatSettingService.makeResponse(storeId));
    }

    @PostMapping("/add")
    public String addSeat(@RequestBody @Valid NewSeatRequest addSeatRequest) {
        seatSettingService.addSeat(addSeatRequest.getStoreId(), addSeatRequest.getSeatName());
        return "추가 완료";
    }

    @DeleteMapping("/delete/{seatId}")
    public String deleteSeat(@PathVariable Long seatId) {
        seatSettingService.deleteSeat(seatId);
        return "삭제 완료";
    }


}
