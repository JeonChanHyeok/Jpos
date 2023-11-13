package com.example.jpos_server.repository;

import com.example.jpos_server.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}