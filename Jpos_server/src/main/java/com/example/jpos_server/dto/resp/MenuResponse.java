package com.example.jpos_server.dto.resp;

import com.example.jpos_server.dto.CategoryDto;
import com.example.jpos_server.dto.MenuDto;
import com.example.jpos_server.repository.MenuRepository;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class MenuResponse {
    String storeName;
    String seatName;
    List<MenuDto> menuDtoList;
    List<CategoryDto> categoryDtoList;


}
