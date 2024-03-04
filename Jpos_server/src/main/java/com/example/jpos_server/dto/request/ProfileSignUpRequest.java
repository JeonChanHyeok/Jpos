package com.example.jpos_server.dto.request;

import com.example.jpos_server.domain.User.Role;
import lombok.Getter;

@Getter
public class ProfileSignUpRequest {
    private String loginId;
    private String loginPw;
    private String userName;
    private Long storeId;
    private Role role;
}
