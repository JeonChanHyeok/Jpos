package com.example.jpos_server.controller;


import com.example.jpos_server.service.CheckService;
import com.example.jpos_server.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 가게 정보 요청 컨트롤러
 */
@RequestMapping("/jpos/store")
@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;
    private final CheckService checkService;

    /**
     * 로그인한 유저의 가게 이름을 반환한다.
     *
     * @param storeLoginId - 가게Id
     * @return 가게 이름
     */
    @GetMapping("{storeLoginId}")
    public String loadStoreName(@RequestHeader("Authorization") String token, @PathVariable Long storeLoginId) {
        checkService.checkValidUserForRequest(token, storeLoginId, 0);
        return storeService.searchStore(storeLoginId).getStoreName();
    }



}
