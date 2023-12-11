package com.example.jpos_server.repository;

import com.example.jpos_server.domain.Category;
import com.example.jpos_server.domain.Store;
import com.example.jpos_server.dto.CategoryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<CategoryDto> findByStore(Store store);
}