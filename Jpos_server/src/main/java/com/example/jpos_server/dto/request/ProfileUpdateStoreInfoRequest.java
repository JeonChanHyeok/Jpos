package com.example.jpos_server.dto.request;

import lombok.Getter;

@Getter
public class ProfileUpdateStoreInfoRequest {
    String description;
    String callNum;
    String location;
}
