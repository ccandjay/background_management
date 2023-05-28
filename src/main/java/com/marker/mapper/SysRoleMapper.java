package com.marker.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.marker.entity.SysRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author YSZ
 * @since 2023-05-15
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    @Select("select id from sys_role where flag = #{flag}")
    Integer selectByFlag(@Param("flag") String flag);
}
