package com.example.jpos_server.dto.resp;

import com.example.jpos_server.dto.CategoryDto;
import com.example.jpos_server.dto.MenuDto;
import com.example.jpos_server.dto.PosOrderDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuAndOrderResponse {
    String storeName;
    String seatName;
    List<MenuDto> menuDtoList;
    List<CategoryDto> categoryDtoList;
    PosOrderDto posOrderDto;


}
