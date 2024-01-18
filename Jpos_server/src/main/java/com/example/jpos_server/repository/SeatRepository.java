package com.example.jpos_server.repository;

import com.example.jpos_server.domain.Seat;
import com.example.jpos_server.domain.Store;
import com.example.jpos_server.dto.SeatDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<SeatDto.SeatDtoOfIdAndSeatName> findIdAndSeatNameByStoreId(Long storeId);
    SeatDto.SeatDtoOfSeatNameAndPosUsing findSeatNameAndPosUsingById(Long id);
}