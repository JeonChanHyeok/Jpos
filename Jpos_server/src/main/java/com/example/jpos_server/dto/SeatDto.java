package com.example.jpos_server.dto;

public class SeatDto {
    public record SeatDtoOfIdAndSeatName(Long id, String seatName) {
    }
    public record SeatDtoOfSeatNameAndPosUsing(String seatName, int posUsing){}
}
