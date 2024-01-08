package com.example.jpos_server.controller;

import com.example.jpos_server.domain.request.SignUpRequest;
import com.example.jpos_server.service.UserAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignUpController {
    private final UserAccountService userAccountService;

    @PostMapping("/jpos/user/signup")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpRequest request) throws Exception{
        userAccountService.signUpOwner(request);
        return ResponseEntity.ok().build();
    }



}
