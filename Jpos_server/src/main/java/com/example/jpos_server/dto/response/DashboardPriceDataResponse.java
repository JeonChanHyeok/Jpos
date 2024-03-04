package com.example.jpos_server.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DashboardPriceDataResponse {
    LocalDateTime storeCreatedDate;
    int todayPrice;
    int todayCount;
    int monthPrice;
    int allPrice;
}
