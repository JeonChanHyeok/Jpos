package com.example.jpos_server.repository;

import com.example.jpos_server.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SeatRepository extends JpaRepository<Seat, Long> {
}