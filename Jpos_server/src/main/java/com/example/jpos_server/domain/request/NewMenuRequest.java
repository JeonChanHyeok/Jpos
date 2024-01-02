package com.example.jpos_server.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewMenuRequest {
    String menuName;
    Long storeId;
    Long menuCategory;
    int menuPrice;
}
