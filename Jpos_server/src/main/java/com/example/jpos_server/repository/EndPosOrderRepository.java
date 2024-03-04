package com.example.jpos_server.repository;

import com.example.jpos_server.domain.EndPosOrder;
import com.example.jpos_server.dto.EndPosOrderDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RepositoryRestResource
public interface EndPosOrderRepository extends JpaRepository<EndPosOrder, Long> {
    List<EndPosOrderDto.EndPosOrderDtoOfPosOrderPrice> findPosOrderPriceByStoreIdAndCreatedDateBetween(Long storeId, LocalDateTime start, LocalDateTime end);
    List<EndPosOrderDto.EndPosOrderDtoOfCreatedDateAndPosOrderContentAndPosOrderPriceAndSeatName> findCreatedDateAndPosOrderContentAndPosOrderPriceAndSeatNameByStoreIdAndCreatedDateBetween(Long storeId, LocalDateTime start, LocalDateTime end);
}