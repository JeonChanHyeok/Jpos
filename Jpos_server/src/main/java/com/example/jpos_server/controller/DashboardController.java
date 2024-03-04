package com.example.jpos_server.controller;

import com.example.jpos_server.service.DashboardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * dashboard 컨트롤러
 * 이전 주문에 대한 정보를 검색한다.
 */
@RequestMapping("/jpos/dashboard")
@RestController
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    /**
     * 대시보드 상단에 들어갈 정보 반환
     *
     * @param storeId - 가게 Id
     * @return 오늘 매출, 오늘 주문 수, 30일 매출, 전체 매출
     * @throws JsonProcessingException - writeValueAsString 사용에 필요
     */
    @GetMapping("/{storeId}")
    public String loadDashboardData(@PathVariable Long storeId) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(dashboardService.makeDashboardPriceDataResponse(storeId));
    }

    /**
     * 대시보드 이전 주문 정보 반환
     * 검색 시작 날짜가 검색 끝 날짜보다 이후일 경우 예외 발생 예정
     *
     * @param storeId - 가게 Id
     * @param startDate - 주문 정보 검색 시작 날짜
     * @param endDate - 주문 정보 검색 끝 날짜
     * @return 주문 결제 시간, 주문 내용, 주문 자리, 주문 가격
     * @throws JsonProcessingException - writeValueAsString 사용에 필요
     */
    @GetMapping("/find/{storeId}/{startDate}/{endDate}")
    public String findEndPosOrder(@PathVariable Long storeId, @PathVariable String startDate, @PathVariable String endDate) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(dashboardService.makeDashboardEndPosOrderDateResponse(storeId, startDate, endDate));
    }

}
