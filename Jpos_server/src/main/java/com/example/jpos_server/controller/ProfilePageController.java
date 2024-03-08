package com.example.jpos_server.controller;

import com.example.jpos_server.dto.request.ProfileSignUpRequest;
import com.example.jpos_server.dto.request.ProfileUpdateStoreInfoRequest;
import com.example.jpos_server.service.CheckService;
import com.example.jpos_server.service.ProfileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * profile 컨트롤러
 * 직원 등록, 삭제, 가게 세부정보 수정
 */
@RequestMapping("/jpos/profile")
@RestController
@RequiredArgsConstructor
public class ProfilePageController {
    private final ProfileService profileService;
    private final CheckService checkService;

    /**
     * profile 화면에서 사용할 데이터 반환
     *
     * @param loginId - 로그인한 유저의 Id
     * @return - 가게의 직원 ID 리스트, 가게 직원 이름 리스트, 가게 직원 권한 리스트, 가게 세부정보, 가입일자
     * @throws JsonProcessingException - writeValueAsString 사용
     */
    @GetMapping("/{loginId}")
    public String loadProfileData(@RequestHeader("Authorization") String token, @PathVariable String loginId) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(profileService.makeProfileResponse(token, loginId));
    }

    /**
     * 직원 추가
     *
     * @param request - 새 직원의 Id, Pw, 이름, 권한, 가게 Id,
     * @return - Ok
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestHeader("Authorization") String token, @RequestBody @Valid ProfileSignUpRequest request) {
        checkService.checkValidUserForRequest(token, request.getStoreId(), 0);
        profileService.signUp(request);
        return ResponseEntity.ok().build();
    }

    /**
     * 직원 삭제
     *
     * @param userId - 삭제할 직원 Id
     * @return - "삭제 완료"
     */
    @DeleteMapping("/delete/{userId}")
    public String deleteEmployee(@RequestHeader("Authorization") String token, @PathVariable String userId){
        profileService.deleteEmployee(token, userId);
        return "삭제 완료";
    }

    /**
     * 가게 세부정보 수정
     *
     * @param storeId - 가게 Id
     * @param profileUpdateStoreInfoRequest - 수정할 내용
     * @return - "수정 완료"
     */
    @PatchMapping("/storeInfo/update/{storeId}")
    public String updateStoreInfo(@RequestHeader("Authorization") String token, @PathVariable Long storeId, @RequestBody @Valid ProfileUpdateStoreInfoRequest profileUpdateStoreInfoRequest){
        checkService.checkValidUserForRequest(token, storeId, 0);
        profileService.updateStoreInfo(storeId, profileUpdateStoreInfoRequest);
        return "수정 완료";
    }

}
