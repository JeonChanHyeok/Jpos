package com.example.jpos_server.domain.request;

import lombok.Getter;

@Getter
public class AddSeatRequest {
    String storeLoginId;
    String seatName;

    public AddSeatRequest(String storeLoginId, String seatName){
        this.storeLoginId = storeLoginId;
        this.seatName = seatName;
    }
}
