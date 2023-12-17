package com.example.jpos_server.dto;

import com.example.jpos_server.domain.PosOrder;

import java.io.Serializable;

/**
 * DTO for {@link com.example.jpos_server.domain.PosOrder}
 */
public record PosOrderDto(Long id, String posOrderContent, int posOrderPrice, Long storeId, Long seatId,
                          int state){

    public static PosOrderDto of(Long id, String posOrderContent, int posOrderPrice, Long storeId, Long seatId,
                              int state){
        return new PosOrderDto(id, posOrderContent, posOrderPrice, storeId, seatId, state);
    }

    public static PosOrderDto form(PosOrder posOrder){
        return new PosOrderDto(
                posOrder.getId(),
                posOrder.getPosOrderContent(),
                posOrder.getPosOrderPrice(),
                posOrder.getStore().getId(),
                posOrder.getSeat().getId(),
                posOrder.getState()
        );
    }
}