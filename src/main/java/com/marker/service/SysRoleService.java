package com.marker.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.marker.entity.SysRole;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YSZ
 * @since 2023-05-15
 */
public interface SysRoleService extends IService<SysRole> {

    void setRoleMenu(Integer roleId, String ids);
}
