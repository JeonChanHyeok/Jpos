package com.example.jpos_server.controller;

import com.example.jpos_server.domain.request.CategoryRequest;
import com.example.jpos_server.domain.request.NewMenuRequest;
import com.example.jpos_server.domain.request.UpdateMenuRequest;
import com.example.jpos_server.domain.response.MenuAndOrderResponse;
import com.example.jpos_server.service.CategoryService;
import com.example.jpos_server.service.MenuService;
import com.example.jpos_server.service.StoreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jpos/menuSetting")
@RequiredArgsConstructor
public class MenuSettingController {
    private final MenuService menuService;
    private final StoreService storeService;
    private final CategoryService categoryService;

    @GetMapping("/{storeId}")
    public String loadAllMenusAndCategories(@PathVariable Long storeId) throws JsonProcessingException {
        MenuAndOrderResponse menuAndOrderResponse = new MenuAndOrderResponse();
        menuAndOrderResponse.setMenuDtoList(menuService.searchMenus(storeService.searchStore(storeId)));
        menuAndOrderResponse.setCategoryDtoList(categoryService.searchCategories(storeService.searchStore(storeId)));

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(menuAndOrderResponse);
    }

    @PostMapping("/menu/add")
    public String addNewMenu(@RequestBody @Valid NewMenuRequest newMenuRequest){
        menuService.addMenu(newMenuRequest);
        return "추가 완료";
    }

    @DeleteMapping("/menu/delete/{menuId}")
    public String deleteMenu(@PathVariable Long menuId){
        menuService.deleteMenu(menuId);
        return "삭제 완료";
    }

    @PatchMapping("menu/update/{menuId}")
    public String updateMenu(@PathVariable Long menuId, @RequestBody @Valid UpdateMenuRequest updateMenuRequest){
        menuService.updateMenu(menuId, updateMenuRequest);
        return "수정 완료";
    }

    @PostMapping("/category/add")
    public String addNewCategory(@RequestBody @Valid CategoryRequest categoryRequest){
        categoryService.addCategory(categoryRequest);
        return "추가 완료";
    }

    @DeleteMapping("/category/delete/{categoryId}")
    public String deleteCategory(@PathVariable Long categoryId){
        categoryService.deleteCategory(categoryId);
        return "삭제 완료";
    }
}
