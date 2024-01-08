package com.example.jpos_server.controller;

import com.example.jpos_server.domain.request.AddSeatRequest;
import com.example.jpos_server.domain.response.SeatResponse;
import com.example.jpos_server.service.SeatService;
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
    private final SeatService seatService;


    @GetMapping("/{storeId}")
    public String loadSeats(@PathVariable Long storeId) throws JsonProcessingException{
        SeatResponse seatResponse = new SeatResponse();

        seatResponse.setSeatDtoList(seatService.searchSeats(storeId));

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(seatResponse);
    }

    @DeleteMapping("/delete/{seatId}")
    public String deleteSeat(@PathVariable Long seatId){
        seatService.deleteSeat(seatId);
        return "삭제 완료";
    }

    @PostMapping("/add")
    public String addSeat(@RequestBody @Valid AddSeatRequest addSeatRequest){
        seatService.addSeat(addSeatRequest.getStoreId(), addSeatRequest.getSeatName());
        return "추가 완료";
    }
}
