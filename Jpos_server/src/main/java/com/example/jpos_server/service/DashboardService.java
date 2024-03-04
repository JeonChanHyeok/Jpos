package com.example.jpos_server.service;

import com.example.jpos_server.dto.EndPosOrderDto;
import com.example.jpos_server.dto.response.DashboardEncPosOrderResponse;
import com.example.jpos_server.dto.response.DashboardPriceDataResponse;
import com.example.jpos_server.repository.EndPosOrderRepository;
import com.example.jpos_server.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class DashboardService {
    private final StoreRepository storeRepository;
    private final EndPosOrderRepository endPosOrderRepository;

    public DashboardPriceDataResponse makeDashboardPriceDataResponse(Long storeId){
        DashboardPriceDataResponse dashboardPriceDataResponse = new DashboardPriceDataResponse();
        dashboardPriceDataResponse.setStoreCreatedDate(storeRepository.findCreatedDateById(storeId).createdDate());
        List<EndPosOrderDto.EndPosOrderDtoOfPosOrderPrice> todayPriceList = endPosOrderRepository.findPosOrderPriceByStoreIdAndCreatedDateBetween(storeId,LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0)), LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59)));
        int todayPrice = 0;
        for(EndPosOrderDto.EndPosOrderDtoOfPosOrderPrice dto: todayPriceList){
            todayPrice += dto.posOrderPrice();
        }
        dashboardPriceDataResponse.setTodayPrice(todayPrice);
        dashboardPriceDataResponse.setTodayCount(todayPriceList.size());

        List<EndPosOrderDto.EndPosOrderDtoOfPosOrderPrice> monthPriceList = endPosOrderRepository.findPosOrderPriceByStoreIdAndCreatedDateBetween(storeId,LocalDateTime.of(LocalDate.now().minusDays(30), LocalTime.of(0,0,0)), LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59)));
        int monthPrice = 0;
        for(EndPosOrderDto.EndPosOrderDtoOfPosOrderPrice dto: monthPriceList){
            monthPrice += dto.posOrderPrice();
        }
        dashboardPriceDataResponse.setMonthPrice(monthPrice);

        List<EndPosOrderDto.EndPosOrderDtoOfPosOrderPrice> allPriceList = endPosOrderRepository.findPosOrderPriceByStoreIdAndCreatedDateBetween(storeId,LocalDateTime.of(LocalDate.of(2000,1,1), LocalTime.of(0,0,0)), LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59)));
        int allPrice = 0;
        for(EndPosOrderDto.EndPosOrderDtoOfPosOrderPrice dto: allPriceList){
            allPrice += dto.posOrderPrice();
        }
        dashboardPriceDataResponse.setAllPrice(allPrice);

        return dashboardPriceDataResponse;
    }

    public DashboardEncPosOrderResponse makeDashboardEndPosOrderDateResponse(Long storeId, String startDate, String endDate){
        DashboardEncPosOrderResponse dashboardEncPosOrderResponse = new DashboardEncPosOrderResponse();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime start = LocalDateTime.of(LocalDate.parse(startDate, format), LocalTime.of(0,0,0));
        LocalDateTime end = LocalDateTime.of(LocalDate.parse(endDate, format), LocalTime.of(23,59,59));
        dashboardEncPosOrderResponse.setEndPosOrderDtoList(endPosOrderRepository.findCreatedDateAndPosOrderContentAndPosOrderPriceAndSeatNameByStoreIdAndCreatedDateBetween(storeId,start,end));
        return dashboardEncPosOrderResponse;
    }
}
