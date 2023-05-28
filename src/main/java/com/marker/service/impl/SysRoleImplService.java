package com.marker.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.marker.entity.SysMenu;
import com.marker.entity.SysRole;
import com.marker.entity.SysRoleMenu;
import com.marker.mapper.SysRoleMapper;
import com.marker.mapper.SysRoleMenuMapper;
import com.marker.service.SysMenuService;
import com.marker.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author YSZ
 * @since 2023-05-15
 */
@Service
public class SysRoleImplService extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Resource
    private SysRoleMenuMapper roleMenuMapper;

    @Resource
    private SysMenuService menuService;

    @Transactional
    @Override
    public void setRoleMenu(Integer roleId, String ids) {
        // 先删除当前id的所有绑定关系
        int i = roleMenuMapper.deleteByRoleId(roleId);
        // 再把前端传过来的菜单id数组绑定到当前的这个角色id上去
        String[] split = ids.split(",");
        List<Integer> arr = new ArrayList<>();
        for (int j = 0; j < split.length; j++) {
            arr.add(Integer.valueOf(split[i]));
        }

        ArrayList<Integer> integers = CollUtil.newArrayList(arr);
        for (String menuId : split) {
            SysMenu menu = menuService.getById(menuId);
            /**
             * 如果前端在tree里点击子菜单时,传递过来的ids中不会有父级菜单id
             * 需要将 父级菜单的id一并写入数据库
             */
            if (menu.getPid() != null && !integers.contains(menu.getPid())) { // 二级菜单,并且传过来的menuId里面没有他的父级id
                SysRoleMenu roleMenu = new SysRoleMenu();
                roleMenu.setMenuId(menu.getPid());
                roleMenu.setRoleId(roleId);
                roleMenuMapper.insert(roleMenu);
                integers.add(menu.getPid());
            }
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setMenuId(Integer.valueOf(menuId));
            roleMenu.setRoleId(roleId);
            roleMenuMapper.insert(roleMenu);
        }
    }
}
