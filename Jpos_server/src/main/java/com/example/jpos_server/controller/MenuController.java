package com.example.jpos_server.controller;

import com.example.jpos_server.domain.Menu;
import com.example.jpos_server.domain.request.NewMenuRequest;
import com.example.jpos_server.domain.response.MenuAndOrderResponse;
import com.example.jpos_server.repository.SeatRepository;
import com.example.jpos_server.repository.StoreRepository;
import com.example.jpos_server.service.CategoryService;
import com.example.jpos_server.service.MenuService;
import com.example.jpos_server.service.StoreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jpos/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;
    private final StoreService storeService;
    private final CategoryService categoryService;

    @GetMapping("/setting/{storeId}")
    public String loadAllMenusAndCategories(@PathVariable Long storeId) throws JsonProcessingException {
        MenuAndOrderResponse menuAndOrderResponse = new MenuAndOrderResponse();
        menuAndOrderResponse.setMenuDtoList(menuService.searchMenus(storeService.searchStore(storeId)));
        menuAndOrderResponse.setCategoryDtoList(categoryService.searchCategories(storeService.searchStore(storeId)));

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(menuAndOrderResponse);
    }

    @PostMapping("/setting/add")
    public String addNewMenu(@RequestBody @Valid NewMenuRequest newMenuRequest){
        menuService.addMenu(newMenuRequest);
        return "추가 완료";
    }

    @DeleteMapping("/setting/delete/{menuId}")
    public String deleteMenu(@PathVariable Long menuId){
        menuService.deleteMenu(menuId);
        return "삭제 완료";
    }


}
