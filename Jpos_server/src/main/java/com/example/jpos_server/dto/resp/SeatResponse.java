package com.example.jpos_server.dto.resp;

import com.example.jpos_server.dto.MenuDto;
import com.example.jpos_server.dto.PosOrderDto;
import com.example.jpos_server.dto.SeatDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SeatResponse {
    List<SeatDto> seatDtoList;
    List<PosOrderDto> posOrderDtoLost;
    List<MenuDto> menuDtoList;
}
