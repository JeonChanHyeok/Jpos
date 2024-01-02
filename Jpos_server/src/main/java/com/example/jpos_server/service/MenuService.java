package com.example.jpos_server.service;

import com.example.jpos_server.domain.Menu;
import com.example.jpos_server.domain.Store;
import com.example.jpos_server.domain.request.NewMenuRequest;
import com.example.jpos_server.dto.MenuDto;
import com.example.jpos_server.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final StoreService storeService;
    private final CategoryService categoryService;

    @Transactional(readOnly = true)
    public List<MenuDto> searchMenus(Store store){
        return menuRepository.findByStore(store);
    }

    public void addMenu(NewMenuRequest newMenuRequest){
        Menu newMenu = new Menu(newMenuRequest.getMenuName(), storeService.searchStore(newMenuRequest.getStoreId()), categoryService.searchCategory(newMenuRequest.getMenuCategory()), newMenuRequest.getMenuPrice());
        menuRepository.save(newMenu);
    }

    public void deleteMenu(Long menuId){
        menuRepository.deleteById(menuId);
    }
}
