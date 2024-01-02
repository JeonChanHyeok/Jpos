package com.example.jpos_server.repository;

import com.example.jpos_server.domain.User.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
}