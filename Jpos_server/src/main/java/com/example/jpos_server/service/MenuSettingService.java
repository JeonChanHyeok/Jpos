package com.example.jpos_server.service;

import com.example.jpos_server.domain.Category;
import com.example.jpos_server.domain.Menu;
import com.example.jpos_server.dto.request.NewCategoryRequest;
import com.example.jpos_server.dto.request.NewMenuRequest;
import com.example.jpos_server.dto.request.UpdateMenuRequest;
import com.example.jpos_server.dto.response.MenuSettingResponse;
import com.example.jpos_server.repository.CategoryRepository;
import com.example.jpos_server.repository.MenuRepository;
import com.example.jpos_server.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class MenuSettingService {
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;

    public MenuSettingResponse makeResponse(Long storeId) {
        MenuSettingResponse menuSettingResponse = new MenuSettingResponse();
        menuSettingResponse.setMenuDtoList(menuRepository.findIdAndMenuNameAndPriceAndCategoryIdByStoreId(storeId));
        menuSettingResponse.setCategoryDtoList(categoryRepository.findIdAndCategoryNameByStoreId(storeId));
        return menuSettingResponse;
    }

    public void addMenu(NewMenuRequest newMenuRequest) {
        Menu newMenu = new Menu(newMenuRequest.getMenuName(), storeRepository.findById(newMenuRequest.getStoreId()).get(), categoryRepository.findById(newMenuRequest.getMenuCategory()).get(), newMenuRequest.getMenuPrice());
        menuRepository.save(newMenu);
    }

    public void deleteMenu(Long menuId) {
        menuRepository.deleteById(menuId);
    }

    @Transactional
    public void updateMenu(Long menuId, UpdateMenuRequest updateMenuRequest) {
        Menu menu = menuRepository.findById(menuId).get();
        menu.setMenuName(updateMenuRequest.getMenuName());
        menu.setCategory(categoryRepository.findById(updateMenuRequest.getMenuCategory()).get());
        menu.setPrice(updateMenuRequest.getMenuPrice());
    }

    public void addCategory(NewCategoryRequest categoryRequest) {

        Category category = new Category();
        category.setCategoryName(categoryRequest.getCategoryName());
        category.setStore(storeRepository.findById(categoryRequest.getStoreId()).get());
        categoryRepository.save(category);
    }

    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }


}
