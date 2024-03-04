package com.example.jpos_server.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EndPosOrderDto {
    public record EndPosOrderDtoOfPosOrderPrice(int posOrderPrice){}
    public record EndPosOrderDtoOfCreatedDateAndPosOrderContentAndPosOrderPriceAndSeatName(LocalDateTime createdDate, String posOrderContent, int posOrderPrice, String seatName){}
}
