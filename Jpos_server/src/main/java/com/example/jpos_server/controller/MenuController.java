package com.example.jpos_server.controller;

import com.example.jpos_server.dto.resp.MenuResponse;
import com.example.jpos_server.repository.SeatRepository;
import com.example.jpos_server.repository.StoreRepository;
import com.example.jpos_server.service.CategoryService;
import com.example.jpos_server.service.MenuService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jpos/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;
    private final CategoryService categoryService;
    private final StoreRepository storeRepository;
    private final SeatRepository seatRepository;


    @GetMapping("/{storeId}/{seatId}")
    public String test(@PathVariable Long storeId, @PathVariable Long seatId, ModelMap map) throws JsonProcessingException {
        MenuResponse menuResponse = new MenuResponse();

        menuResponse.setStoreName(storeRepository.findById(storeId).get().getStoreName());
        menuResponse.setSeatName(seatRepository.findById(seatId).get().getSeatName());
        menuResponse.setMenuDtoList(menuService.searchMenus(storeRepository.findById(storeId).get()));
        menuResponse.setCategoryDtoList(categoryService.searchCategories(storeRepository.findById(storeId).get()));

        ObjectMapper objectMapper = new ObjectMapper();

        String respJson = objectMapper.writeValueAsString(menuResponse);
        return respJson;
    }

}
