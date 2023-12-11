package com.example.jpos_server.dto;

import com.example.jpos_server.domain.Category;

import java.io.Serializable;

/**
 * DTO for {@link com.example.jpos_server.domain.Category}
 */
public record CategoryDto(Long id, String categoryName, Long storeId){
    public static CategoryDto of(Long id, String categoryName, Long storeId){
        return new CategoryDto(id, categoryName, storeId);
    }

    public static CategoryDto from(Category category){
        return new CategoryDto(
                category.getId(),
                category.getCategoryName(),
                category.getStore().getId()
        );
    }
}