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
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/jpos/store")
@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreRepository storeRepository;
    private final StoreService storeService;
    private final SeatService seatService;

    @PostMapping("/sign-up")
    @ResponseBody
    public String signUp(StoreDto storeDto){
        if(storeService.makeStore(storeDto)){
            return "가입완료";
        }else{
            return "가입실패";
        }
    }

    @GetMapping("{storeLoginId}")
    public String loadStoreName(@PathVariable String storeLoginId){
        return storeService.searchStore(storeLoginId).getStoreName();
    }

    @GetMapping("/qr/{storeLoginId}")
    public String loadSeatsForQr(@PathVariable String storeLoginId) throws JsonProcessingException {
        QrResponse qrResponse = new QrResponse();

        qrResponse.setStoreId(storeRepository.findByStoreLoginId(storeLoginId).getId());
        qrResponse.setSeatDtoList(seatService.searchSeats(storeLoginId));

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(qrResponse);
    }


}
