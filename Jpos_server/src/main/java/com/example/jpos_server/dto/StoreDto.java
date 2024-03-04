package com.example.jpos_server.dto;

import java.time.LocalDateTime;

public class StoreDto {
    public record StoreDtoOfStoreName(String storeName){}
    public record StoreDtoOfCreatedDate(LocalDateTime createdDate){}
}
