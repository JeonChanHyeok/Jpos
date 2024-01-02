package com.example.jpos_server.repository;

import com.example.jpos_server.domain.Store;
import com.example.jpos_server.dto.StoreDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface StoreRepository extends JpaRepository<Store, Long> {
}