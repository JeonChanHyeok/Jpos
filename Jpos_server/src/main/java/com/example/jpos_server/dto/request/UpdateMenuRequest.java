package com.example.jpos_server.dto.request;

import lombok.Getter;

@Getter
public class UpdateMenuRequest {
    String menuName;
    Long menuCategory;
    int menuPrice;
}
