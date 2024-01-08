package com.example.jpos_server.controller;


import com.example.jpos_server.dto.StoreDto;
import com.example.jpos_server.domain.response.QrResponse;
import com.example.jpos_server.repository.StoreRepository;
import com.example.jpos_server.service.SeatService;
import com.example.jpos_server.service.StoreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/jpos/store")
@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreRepository storeRepository;
    private final StoreService storeService;
    private final SeatService seatService;

    @GetMapping("{storeLoginId}")
    public String loadStoreName(@PathVariable Long storeLoginId){
        return storeService.searchStore(storeLoginId).getStoreName();
    }

    @GetMapping("/qr/{storeLoginId}")
    public String loadSeatsForQr(@PathVariable Long storeLoginId) throws JsonProcessingException {
        QrResponse qrResponse = new QrResponse();

        qrResponse.setStoreId(storeLoginId);
        qrResponse.setSeatDtoList(seatService.searchSeats(storeLoginId));

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(qrResponse);
    }


}
