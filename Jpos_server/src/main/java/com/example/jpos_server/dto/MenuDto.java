package com.example.jpos_server.dto;

import com.example.jpos_server.domain.Menu;

/**
 * DTO for {@link com.example.jpos_server.domain.Menu}
 */
public record MenuDto(Long id, String menuName, Long storeId, Long categoryId, int price){

    public static MenuDto of(Long id, String menuName, Long storeId, Long categoryId, int price){
        return new MenuDto(id, menuName, storeId, categoryId, price);
    }

    public static MenuDto from(Menu menu){
        return new MenuDto(
                menu.getId(),
                menu.getMenuName(),
                menu.getStore().getId(),
                menu.getCategory().getId(),
                menu.getPrice()
        );
    }
}