package com.example.jpos_server.dto.request;

import lombok.Getter;

@Getter
public class NewSeatRequest {
    Long storeId;
    String seatName;

}
