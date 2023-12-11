package com.example.jpos_server.dto.resp;

import com.example.jpos_server.dto.CategoryDto;

public record CategoryResponse (Long id, String categoryName) {
    public static CategoryResponse of(Long id, String categoryName){
        return new CategoryResponse(id, categoryName);
    }

    public static CategoryResponse from(CategoryDto categoryDto){
        return new CategoryResponse(
                categoryDto.id(),
                categoryDto.categoryName()
        );
    }
}
