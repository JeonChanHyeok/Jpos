package com.example.jpos_server.dto.response;

import com.example.jpos_server.dto.CategoryDto;
import com.example.jpos_server.dto.MenuDto;
import com.example.jpos_server.dto.PosOrderDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QrOrderResponse {
    String storeName;
    String seatName;
    int posUsing;
    List<MenuDto.MenuDtoOfIdAndMenuNameAndPriceAndCategoryId> menuDtoList;
    List<CategoryDto.CategoryDtoOfIdAndCategoryName> categoryDtoList;
    PosOrderDto.PosOrderDtoOfIdAndPosOrderContentAndPosOrderPrice posOrderDto;
}
