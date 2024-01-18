package com.example.jpos_server.dto.request;

import lombok.Getter;

@Getter
public class NewCategoryRequest {
    private String categoryName;
    private Long storeId;
}
