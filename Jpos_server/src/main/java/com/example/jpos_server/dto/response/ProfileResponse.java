package com.example.jpos_server.dto.response;

import com.example.jpos_server.domain.User.Role;
import com.example.jpos_server.dto.StoreInfoDto;
import com.example.jpos_server.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ProfileResponse {
    List<String> userIdList;
    List<String> userNameList;
    List<Role> userRoleList;
    StoreInfoDto.StoreInfoDtoOfDescriptionAndCallNullAndLocation storeInfoDto;
    LocalDateTime storeCreatedAt;
}
