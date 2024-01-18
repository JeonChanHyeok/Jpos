package com.example.jpos_server.dto.response;

import com.example.jpos_server.dto.CategoryDto;
import com.example.jpos_server.dto.MenuDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuSettingResponse {
    List<CategoryDto.CategoryDtoOfIdAndCategoryName> categoryDtoList;
    List<MenuDto.MenuDtoOfIdAndMenuNameAndPriceAndCategoryId> menuDtoList;
}
