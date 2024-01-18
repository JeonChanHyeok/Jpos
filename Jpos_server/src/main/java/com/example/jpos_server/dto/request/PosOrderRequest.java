package com.example.jpos_server.dto.request;

import lombok.Getter;

@Getter
public class PosOrderRequest {
    Long id;
    String posOrderContent;
    int posOrderPrice;
    Long storeId;
    Long seatId;
}
