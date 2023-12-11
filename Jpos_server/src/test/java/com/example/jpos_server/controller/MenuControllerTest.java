package com.example.jpos_server.controller;

import com.example.jpos_server.service.MenuService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Menu 컨트롤러")
@WebMvcTest(MenuController.class)
class MenuControllerTest {

    private final MockMvc mvc;

    @MockBean private MenuService menuService;

    MenuControllerTest(
            @Autowired MockMvc mvc
    ){
        this.mvc = mvc;
    }

    @DisplayName("[GET] 메뉴판 페이지 호출")
    @Test
    void givenStoreIdAndSeatId_whenRequestingMenuView_thenReturnMenuView() throws Exception {
        //Given

        //When & Then
        mvc.perform(get("/menus"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("test"));

    }


}