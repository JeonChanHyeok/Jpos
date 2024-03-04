package com.example.jpos_server.service;

import com.example.jpos_server.config.error.ErrorCode;
import com.example.jpos_server.config.error.exceptions.LoginIdDuplicateException;
import com.example.jpos_server.domain.Store;
import com.example.jpos_server.domain.StoreInfo;
import com.example.jpos_server.domain.User.Authority;
import com.example.jpos_server.domain.User.UserAccount;
import com.example.jpos_server.dto.request.SignUpRequest;
import com.example.jpos_server.repository.AuthorityRepository;
import com.example.jpos_server.repository.StoreInfoRepository;
import com.example.jpos_server.repository.StoreRepository;
import com.example.jpos_server.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAccountService {
    private final UserAccountRepository userAccountRepository;
    private final AuthorityRepository authorityRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final StoreRepository storeRepository;
    private final StoreInfoRepository storeInfoRepository;

    @Transactional
    public void signUp(SignUpRequest request) throws Exception {

        if(userAccountRepository.existsByLoginId(request.getLoginId())){
            throw new LoginIdDuplicateException("아이디 중복", ErrorCode.LOGIN_ID_DUPLICATION);
        }


        Set<Authority> roles = new HashSet<>();
        Authority authority = Authority.builder().name(request.getRole()).build();
        authorityRepository.save(authority);
        roles.add(authority);

        log.info("data = {}", request.getUserName());
        Store store = new Store(request.getStoreName(), request.getLatitude(), request.getLongitude());
        StoreInfo storeInfo = new StoreInfo(store, "", "", "");
        storeRepository.save(store);
        storeInfoRepository.save(storeInfo);
        userAccountRepository.save(UserAccount.builder()
                .loginId(request.getLoginId())
                .loginPw(passwordEncoder.encode(request.getLoginPw()))
                .roles(roles)
                .userName(request.getUserName())
                .store(store)
                .build());
    }

    @Transactional(readOnly = true)
    public Long searchStoreIdByUserId(String userId){
        return userAccountRepository.findByLoginId(userId).get().getStore().getId();
    }

}
