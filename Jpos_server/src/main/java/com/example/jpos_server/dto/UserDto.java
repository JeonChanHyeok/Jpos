package com.example.jpos_server.dto;

import com.example.jpos_server.domain.User.Authority;

import java.util.Set;

public class UserDto {
    public record UserDtoOfIdAndUserNameAndRoles(Long id, String userName, Set<Authority> roles){}
}
