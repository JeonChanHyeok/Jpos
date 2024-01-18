package com.example.jpos_server.controller;


import com.example.jpos_server.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/jpos/store")
@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping("{storeLoginId}")
    public String loadStoreName(@PathVariable Long storeLoginId){
        return storeService.searchStore(storeLoginId).getStoreName();
    }

}
