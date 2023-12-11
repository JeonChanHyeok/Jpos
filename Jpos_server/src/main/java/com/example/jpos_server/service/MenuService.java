package com.example.jpos_server.service;

import com.example.jpos_server.domain.Menu;
import com.example.jpos_server.domain.Store;
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

    @Transactional(readOnly = true)
    public List<MenuDto> searchMenus(Store store){
        return menuRepository.findByStore(store);
    }

}
