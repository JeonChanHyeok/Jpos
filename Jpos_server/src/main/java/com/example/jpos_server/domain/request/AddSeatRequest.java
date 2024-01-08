package com.example.jpos_server.domain.request;

import lombok.Getter;

@Getter
public class AddSeatRequest {
    Long storeId;
    String seatName;

    public AddSeatRequest(Long storeId, String seatName){
        this.storeId = storeId;
        this.seatName = seatName;
    }
}
