package com.example.jpos_server.controller;

import com.example.jpos_server.config.security.UserAccountDetails;
import com.example.jpos_server.config.security.jwt.JwtUtils;
import com.example.jpos_server.dto.request.LoginRequest;
import com.example.jpos_server.dto.response.JwtResponse;
import com.example.jpos_server.service.UserAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 로그인 컨트롤러
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserAccountService userAccountService;


    /**
     * 로그인
     * 로그인 정보와 로그인 정보를 바탕으로 생성된 jwt 토큰을 반환한다.
     * 로그인 실패 시 BadCredentialException 발생 (없는 Id - 원래 UsernameNotFoundException 발생 시켜야 하지만 spring security가 보안을 위해 변경해줌, 틀린 비밀번호)
     *
     * @param loginRequest - 로그인 Id, 로그인 Pw
     * @return 생성된 jwt 토큰, 로그인한 유저의 Id(식별번호), 로그인 Id, 이름, 권한, 식당
     */
    @PostMapping("/jpos/user/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getLoginId(), loginRequest.getLoginPw()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.issueJwtToken(authentication);

        UserAccountDetails userAccountDetails = (UserAccountDetails) authentication.getPrincipal();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer" + jwt);

        List<String> roles = userAccountDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new ResponseEntity<>(new JwtResponse(jwt, userAccountDetails.getId(), userAccountDetails.getUsername(), userAccountDetails.getLoginId(), roles, userAccountService.searchStoreIdByUserId(userAccountDetails.getLoginId())), httpHeaders, HttpStatus.OK);
    }
}
