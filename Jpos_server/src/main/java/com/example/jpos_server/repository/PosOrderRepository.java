package com.example.jpos_server.repository;

import com.example.jpos_server.domain.PosOrder;
import com.example.jpos_server.domain.Seat;
import com.example.jpos_server.domain.Store;
import com.example.jpos_server.dto.PosOrderDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PosOrderRepository extends JpaRepository<PosOrder, Long> {
    Optional<PosOrderDto> findBySeat(Seat seat);
    List<PosOrderDto> findByStore(Store store);
}