package com.example.jpos_server.repository;

import com.example.jpos_server.domain.StoreInfo;
import com.example.jpos_server.dto.StoreInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreInfoRepository extends JpaRepository<StoreInfo, Long> {
    StoreInfoDto.StoreInfoDtoOfDescriptionAndCallNullAndLocation findDescriptionAndCallNumAndLocationByStoreId(Long storeId);
    Optional<StoreInfo> findByStoreId(Long storeId);
}