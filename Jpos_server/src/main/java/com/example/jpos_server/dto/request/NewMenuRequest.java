package com.example.jpos_server.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
public class NewMenuRequest {
    String menuName;
    Long storeId;
    Long menuCategory;
    int menuPrice;
}
