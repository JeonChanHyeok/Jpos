package com.example.jpos_server.domain.response;

import com.example.jpos_server.dto.SeatDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QrResponse {
    Long storeId;
    List<SeatDto> seatDtoList;
}
