package com.marker.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.marker.controller.dto.UserPasswordDto;
import com.marker.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author YSZ
 * @since 2023-05-15
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    Page<SysUser> findPage(Page<SysUser> page, @Param("username") String username, @Param("email") String email, @Param("address") String address);

    @Update("update sys_user set password = #{newPassword} where username = #{username} and password = #{password}")
    int updatePassword(UserPasswordDto userPasswordDTO);
}
