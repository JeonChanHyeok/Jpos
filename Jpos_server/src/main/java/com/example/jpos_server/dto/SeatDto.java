package com.example.jpos_server.dto;

import com.example.jpos_server.domain.Seat;

import java.io.Serializable;

/**
 * DTO for {@link com.example.jpos_server.domain.Seat}
 */
public record SeatDto(Long id, String seatName){

    public static SeatDto of(Long id, String seatName){
        return new SeatDto(id, seatName);
    }

    public static SeatDto from(Seat seat){
        return new SeatDto(
                seat.getId(),
                seat.getSeatName()
        );
    }
}