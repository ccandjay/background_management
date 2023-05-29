package com.marker.controller.dto;

import lombok.Data;

@Data
public class UserPasswordDto {
    private String username;
    private String password;
    private String newPassword;
}
