package com.example.jpos_server.controller;

import com.example.jpos_server.dto.request.SignUpRequest;
import com.example.jpos_server.service.UserAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * SignUp 컨트롤러
 * 가게등록과 회원가입
 */
@RestController
@RequiredArgsConstructor
public class SignUpController {
    private final UserAccountService userAccountService;

    /**
     * 회원가입(가게등록)
     * @param request
     * @return ok
     * @throws Exception
     */
    @PostMapping("/jpos/user/signup")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpRequest request) throws Exception{
        userAccountService.signUp(request);
        return ResponseEntity.ok().build();
    }



}
