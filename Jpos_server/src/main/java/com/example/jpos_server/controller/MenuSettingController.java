package com.example.jpos_server.controller;

import com.example.jpos_server.dto.request.NewCategoryRequest;
import com.example.jpos_server.dto.request.NewMenuRequest;
import com.example.jpos_server.dto.request.UpdateMenuRequest;
import com.example.jpos_server.service.CheckService;
import com.example.jpos_server.service.IdempotencyService;
import com.example.jpos_server.service.MenuSettingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 메뉴 수정 컨트롤러
 * 메뉴 정보를 수정한다.
 */
@RestController
@RequestMapping("/jpos/menuSetting")
@RequiredArgsConstructor
public class MenuSettingController {
    private final MenuSettingService menuSettingService;
    private final IdempotencyService idempotencyService;
    private final CheckService checkService;

    /**
     * 가게의 모든 메뉴와 카테고리 정보를 반환한다.
     *
     * @param storeId - 가게Id
     * @return 가게의 모든 메뉴와 카테고리 정보
     * @throws JsonProcessingException - writeValueAsString 사용을 위한 throws
     */
    @GetMapping("/{storeId}")
    public String loadAllMenusAndCategories(@RequestHeader("Authorization") String token, @PathVariable Long storeId) throws JsonProcessingException {
        checkService.checkValidUserForRequest(token, storeId, 0);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(menuSettingService.makeResponse(storeId));
    }

    /**
     * 새 메뉴를 등록한다.
     *
     * @param newMenuRequest - 새 메뉴 정보(이름, 가게 Id, 카테고리 Id, 가격)
     * @return "추가 완료"
     */
    @PostMapping("/menu/add")
    public String addNewMenu(@RequestHeader("Authorization") String token, @RequestHeader("Idempotency-Key") String idempotencyKey, @RequestBody @Valid NewMenuRequest newMenuRequest){
        idempotencyService.checkDuplicatedRequest(idempotencyKey);
        checkService.checkValidUserForRequest(token, newMenuRequest.getStoreId(), 0);
        menuSettingService.addMenu(newMenuRequest);
        return "추가 완료";
    }

    /**
     * 메뉴를 삭제한다.
     *
     * @param menuId - 메뉴 Id
     * @return "삭제 완료"
     */
    @DeleteMapping("/menu/delete/{menuId}")
    public String deleteMenu(@RequestHeader("Authorization") String token, @PathVariable Long menuId){
        checkService.checkValidUserForRequest(token, menuId, 4);
        menuSettingService.deleteMenu(menuId);
        return "삭제 완료";
    }

    /**
     * 메뉴 정보를 수정한다.
     * 모든 정보가 같은 메뉴가 이미 있을 때 예외 발생 예정
     *
     * @param menuId - 메뉴 Id
     * @param updateMenuRequest - 수정할 메뉴 정보 (이름, 카테고리 Id, 가격)
     * @return "수정 완료"
     */
    @PatchMapping("menu/update/{menuId}")
    public String updateMenu(@RequestHeader("Authorization") String token, @PathVariable Long menuId, @RequestBody @Valid UpdateMenuRequest updateMenuRequest){
        checkService.checkValidUserForRequest(token, menuId, 4);
        menuSettingService.updateMenu(menuId, updateMenuRequest);
        return "수정 완료";
    }

    /**
     * 카테고리 추가
     *
     * @param categoryRequest - 추가할 카테고리 정보 (이름, 가게 Id)
     * @return "추가 완료"
     */
    @PostMapping("/category/add")
    public String addNewCategory(@RequestHeader("Authorization") String token, @RequestHeader("Idempotency-Key") String idempotencyKey, @RequestBody @Valid NewCategoryRequest categoryRequest){
        idempotencyService.checkDuplicatedRequest(idempotencyKey);
        checkService.checkValidUserForRequest(token, categoryRequest.getStoreId(), 0);
        menuSettingService.addCategory(categoryRequest);
        return "추가 완료";
    }

    /**
     * 카테고리 삭제
     * 카테고리 삭제 시 하위 메뉴들도 모두 삭제된다.
     *
     * @param categoryId - 카테고리 Id
     * @return "삭제 완료"
     */
    @DeleteMapping("/category/delete/{categoryId}")
    public String deleteCategory(@RequestHeader("Authorization") String token, @PathVariable Long categoryId){
        checkService.checkValidUserForRequest(token, categoryId, 5);
        menuSettingService.deleteCategory(categoryId);
        return "삭제 완료";
    }
}
