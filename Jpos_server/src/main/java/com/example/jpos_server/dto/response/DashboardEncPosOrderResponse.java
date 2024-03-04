package com.example.jpos_server.dto.response;

import com.example.jpos_server.dto.EndPosOrderDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class DashboardEncPosOrderResponse {
    List<EndPosOrderDto.EndPosOrderDtoOfCreatedDateAndPosOrderContentAndPosOrderPriceAndSeatName> endPosOrderDtoList;
}
