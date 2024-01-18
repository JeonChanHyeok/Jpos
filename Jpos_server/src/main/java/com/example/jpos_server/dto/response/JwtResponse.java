package com.example.jpos_server.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String userName;
    private String loginId;
    private List<String> roles;
    private Long storeId;

    public JwtResponse(String token, Long id, String userName, String loginId, List<String> roles, Long storeId) {
        this.token = token;
        this.id = id;
        this.userName = userName;
        this.loginId = loginId;
        this.roles = roles;
        this.storeId = storeId;
    }
}
