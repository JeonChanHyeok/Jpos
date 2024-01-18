package com.example.jpos_server.dto.request;

import com.example.jpos_server.domain.User.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequest {
    private String loginId;
    private String loginPw;
    private String userName;
    private String storeName;
    private Role role;
    private Double latitude;
    private Double longitude;
}
