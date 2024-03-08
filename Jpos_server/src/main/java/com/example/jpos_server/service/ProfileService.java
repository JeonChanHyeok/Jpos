package com.example.jpos_server.service;

import com.example.jpos_server.config.error.ErrorCode;
import com.example.jpos_server.config.error.exceptions.LoginIdDuplicateException;
import com.example.jpos_server.config.error.exceptions.NotMatchingUserAndRequestException;
import com.example.jpos_server.config.security.jwt.JwtUtils;
import com.example.jpos_server.domain.Store;
import com.example.jpos_server.domain.StoreInfo;
import com.example.jpos_server.domain.User.Authority;
import com.example.jpos_server.domain.User.Role;
import com.example.jpos_server.domain.User.UserAccount;
import com.example.jpos_server.dto.request.ProfileSignUpRequest;
import com.example.jpos_server.dto.request.ProfileUpdateStoreInfoRequest;
import com.example.jpos_server.dto.response.ProfileResponse;

import com.example.jpos_server.repository.AuthorityRepository;
import com.example.jpos_server.repository.StoreInfoRepository;
import com.example.jpos_server.repository.StoreRepository;
import com.example.jpos_server.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class ProfileService {
    private final UserAccountRepository userAccountRepository;
    private final StoreInfoRepository storeInfoRepository;
    private final AuthorityRepository authorityRepository;
    private final StoreRepository storeRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Transactional(readOnly = true)
    public ProfileResponse makeProfileResponse(String token, String loginId) {
        String jwt = token.substring(7);

        String tokenLoginId = jwtUtils.getUserNameFromJwtToken(jwt);

        if(!loginId.equals(tokenLoginId)){
            throw new NotMatchingUserAndRequestException("아이디와 요청 매칭 안됨.", ErrorCode.NOT_MATCH_USER_AND_REQUEST);
        }

        ProfileResponse profileResponse = new ProfileResponse();
        Store store = userAccountRepository.findByLoginId(loginId).get().getStore();
        List<String> userIdList = new ArrayList<>();
        List<String> userNameList = new ArrayList<>();
        List<Role> userRoleList = new ArrayList<>();
        for (UserAccount user : userAccountRepository.findAllByStoreId(store.getId())) {
            userIdList.add(user.getLoginId());
            userNameList.add(user.getUserName());
            userRoleList.add(user.getRoles().iterator().next().getName());
        }
        profileResponse.setUserIdList(userIdList);
        profileResponse.setUserNameList(userNameList);
        profileResponse.setUserRoleList(userRoleList);
        profileResponse.setStoreInfoDto(storeInfoRepository.findDescriptionAndCallNumAndLocationByStoreId(store.getId()));
        profileResponse.setStoreCreatedAt(store.getCreatedDate());

        return profileResponse;
    }

    @Transactional
    public void signUp(ProfileSignUpRequest request) {
        if (userAccountRepository.existsByLoginId(request.getLoginId())) {
            throw new LoginIdDuplicateException("아이디 중복", ErrorCode.LOGIN_ID_DUPLICATION);
        }

        Set<Authority> roles = new HashSet<>();
        Authority authority = Authority.builder().name(request.getRole()).build();
        authorityRepository.save(authority);
        roles.add(authority);

        log.info("data = {}", request.getUserName());
        Store store = storeRepository.findById(request.getStoreId()).get();
        userAccountRepository.save(UserAccount.builder()
                .loginId(request.getLoginId())
                .loginPw(passwordEncoder.encode(request.getLoginPw()))
                .roles(roles)
                .userName(request.getUserName())
                .store(store)
                .build());


    }

    @Transactional
    public void deleteEmployee(String token, String userId) {
        String jwt = token.substring(7);
        String loginId = jwtUtils.getUserNameFromJwtToken(jwt);
        Long storeId = userAccountRepository.findByLoginId(userId).get().getStore().getId();
        if(!Objects.equals(userAccountRepository.findByLoginId(loginId).get().getStore().getId(), storeId)){
            throw new NotMatchingUserAndRequestException("아이디와 요청 매칭 안됨.", ErrorCode.NOT_MATCH_USER_AND_REQUEST);
        }
        userAccountRepository.deleteByLoginId(userId);
    }

    @Transactional
    public void updateStoreInfo(Long storeId, ProfileUpdateStoreInfoRequest request){
        StoreInfo storeInfo = storeInfoRepository.findByStoreId(storeId).get();
        storeInfo.setDescription(request.getDescription());
        storeInfo.setCallNum(request.getCallNum());
        storeInfo.setLocation(request.getLocation());
    }
}
