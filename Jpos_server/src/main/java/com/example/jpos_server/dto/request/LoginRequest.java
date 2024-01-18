package com.example.jpos_server.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class LoginRequest {
    @NotEmpty
    private String loginId;

    @NotEmpty
    private String loginPw;

}
