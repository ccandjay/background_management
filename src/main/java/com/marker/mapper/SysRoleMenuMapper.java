package com.marker.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.marker.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 角色菜单关系表 Mapper 接口
 * </p>
 *
 * @author YSZ
 * @since 2023-05-15
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    @Delete("delete from sys_role_menu where role_id = #{roleId}")
    int deleteByRoleId(@Param("roleId") Integer roleId);


    @Select("select menu_id from sys_role_menu where role_id  = #{roleId}")
    List<Integer> selectByRoleId(@Param("roleId") Integer roleId);
}
