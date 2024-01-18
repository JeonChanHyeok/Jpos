package com.example.jpos_server.dto.response;

import com.example.jpos_server.dto.MenuDto;
import com.example.jpos_server.dto.PosOrderDto;
import com.example.jpos_server.dto.SeatDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PosResponse {
    List<SeatDto.SeatDtoOfIdAndSeatName> seatResponseList;
    List<PosOrderDto.PosOrderDtoOfSeatIdAndPosOrderPriceAndPosOrderContentAndCreatedDate> posOrderResponseList;
    List<MenuDto.MenuDtoOfIdAndMenuNameAndPrice> menuResponseList;
}
