package com.example.jpos_server.controller;

import com.example.jpos_server.dto.request.NewCategoryRequest;
import com.example.jpos_server.dto.request.NewMenuRequest;
import com.example.jpos_server.dto.request.UpdateMenuRequest;
import com.example.jpos_server.service.MenuSettingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jpos/menuSetting")
@RequiredArgsConstructor
public class MenuSettingController {
    private final MenuSettingService menuSettingService;

    @GetMapping("/{storeId}")
    public String loadAllMenusAndCategories(@PathVariable Long storeId) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(menuSettingService.makeResponse(storeId));
    }

    @PostMapping("/menu/add")
    public String addNewMenu(@RequestBody @Valid NewMenuRequest newMenuRequest){
        menuSettingService.addMenu(newMenuRequest);
        return "추가 완료";
    }

    @DeleteMapping("/menu/delete/{menuId}")
    public String deleteMenu(@PathVariable Long menuId){
        menuSettingService.deleteMenu(menuId);
        return "삭제 완료";
    }

    @PatchMapping("menu/update/{menuId}")
    public String updateMenu(@PathVariable Long menuId, @RequestBody @Valid UpdateMenuRequest updateMenuRequest){
        menuSettingService.updateMenu(menuId, updateMenuRequest);
        return "수정 완료";
    }

    @PostMapping("/category/add")
    public String addNewCategory(@RequestBody @Valid NewCategoryRequest categoryRequest){
        menuSettingService.addCategory(categoryRequest);
        return "추가 완료";
    }

    @DeleteMapping("/category/delete/{categoryId}")
    public String deleteCategory(@PathVariable Long categoryId){
        menuSettingService.deleteCategory(categoryId);
        return "삭제 완료";
    }
}
