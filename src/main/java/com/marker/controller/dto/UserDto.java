package com.marker.controller.dto;

import com.marker.entity.SysMenu;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String avatarUrl;
    private String token;
    private String role;
    private List<SysMenu> menus;
}
