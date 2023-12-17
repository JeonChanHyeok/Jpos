package com.example.jpos_server.dto;

import com.example.jpos_server.domain.Store;

import java.io.Serializable;

/**
 * DTO for {@link com.example.jpos_server.domain.Store}
 */
public record StoreDto(String storeName, Double latitude, Double longitude, String storeLoginId,
                       String storeLoginPw) implements Serializable {

    public static StoreDto of(String storeName, Double latitude, Double longitude, String storeLoginId,
                              String storeLoginPw){
        return new StoreDto(storeName, latitude, longitude, storeLoginId, storeLoginPw);
    }
    public static StoreDto from(Store store){
        return new StoreDto(
                store.getStoreName(),
                store.getLatitude(),
                store.getLongitude(),
                store.getStoreLoginId(),
                store.getStoreLoginPw()
        );
    }
}