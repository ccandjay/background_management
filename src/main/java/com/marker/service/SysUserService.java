package com.marker.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.marker.controller.dto.UserDto;
import com.marker.controller.dto.UserPasswordDto;
import com.marker.entity.SysUser;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YSZ
 * @since 2023-05-15
 */
public interface SysUserService extends IService<SysUser> {

    UserDto login(UserDto user);

    void updatePassword(UserPasswordDto userPasswordDTO);
}
