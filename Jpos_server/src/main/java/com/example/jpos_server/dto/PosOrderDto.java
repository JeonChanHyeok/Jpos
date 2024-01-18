package com.example.jpos_server.dto;

import java.time.LocalDateTime;

public class PosOrderDto {
    public record PosOrderDtoOfSeatIdAndPosOrderPriceAndPosOrderContentAndCreatedDate(Long seatId, int posOrderPrice, String posOrderContent, LocalDateTime createdDate){}
    public record PosOrderDtoOfIdAndPosOrderContentAndPosOrderPrice(Long id, String posOrderContent, int posOrderPrice){}
}
