package com.example.jpos_server.domain.request;

import lombok.Getter;

@Getter
public class CategoryRequest {
    private String categoryName;
    private Long storeId;
}
