package com.example.jpos_server.dto;

public class MenuDto {
    public record MenuDtoOfIdAndMenuNameAndPrice(Long id, String menuName, int price){};
    public record MenuDtoOfIdAndMenuNameAndPriceAndCategoryId(Long id, String menuName, int price, Long categoryId){}
}
