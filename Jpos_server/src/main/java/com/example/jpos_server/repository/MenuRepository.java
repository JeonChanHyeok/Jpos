package com.example.jpos_server.repository;

import com.example.jpos_server.domain.Menu;
import com.example.jpos_server.domain.Store;
import com.example.jpos_server.dto.MenuDto;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface MenuRepository extends JpaRepository<Menu, Long>{
    List<MenuDto> findByStore(Store store);
}