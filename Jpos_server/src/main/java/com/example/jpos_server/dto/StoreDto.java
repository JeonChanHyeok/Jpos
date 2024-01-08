package com.example.jpos_server.dto;

import com.example.jpos_server.domain.Store;

import java.io.Serializable;

/**
 * DTO for {@link com.example.jpos_server.domain.Store}
 */
public record StoreDto(String storeName, Double latitude, Double longitude) implements Serializable {

    public static StoreDto of(String storeName, Double latitude, Double longitude){
        return new StoreDto(storeName, latitude, longitude);
    }
    public static StoreDto from(Store store){
        return new StoreDto(
                store.getStoreName(),
                store.getLatitude(),
                store.getLongitude()
        );
    }
}