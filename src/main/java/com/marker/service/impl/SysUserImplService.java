package com.marker.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.marker.common.Constants;
import com.marker.controller.dto.UserDto;
import com.marker.entity.SysMenu;
import com.marker.entity.SysRole;
import com.marker.entity.SysUser;
import com.marker.exception.ServiceException;
import com.marker.mapper.SysRoleMapper;
import com.marker.mapper.SysRoleMenuMapper;
import com.marker.mapper.SysUserMapper;
import com.marker.service.SysMenuService;
import com.marker.service.SysRoleService;
import com.marker.service.SysUserService;
import com.marker.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author YSZ
 * @since 2023-05-15
 */
@Service
@Slf4j
public class SysUserImplService extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysRoleMapper roleService;

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    @Autowired
    private SysMenuService menuService;

    @Override
    public UserDto login(UserDto user) {
        LambdaQueryWrapper<SysUser> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SysUser::getUsername,user.getUsername());
        UserDto userDto = new UserDto();
        SysUser one = getOne(lqw);
        if(one != null && one.getPassword().equals(SecureUtil.md5(user.getPassword()))){
            BeanUtil.copyProperties(one,userDto,true);
            String token = TokenUtils.token(one.getId().toString(), one.getPassword());
            // 获取到角色信息,然后获取和菜单的关联关系
            String role = one.getRole();
            List<SysMenu> menus = getRoleMenuList(role);
            userDto.setMenus(menus);
            userDto.setToken(token);
            return userDto;
        }else{
            log.info("用户登录失败,用户名或密码错误");
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }
    }

    /**
     * 获取当前用户的菜单信息
     * @param role 角色
     * @return 菜单列表
     */
    private List<SysMenu> getRoleMenuList(String role){
        Integer id = roleService.selectByFlag(role);
        // 通过角色id,获取该角色的菜单列表id
        List<Integer> ids = roleMenuMapper.selectByRoleId(id);
        // 查出所有系统菜单
        List<SysMenu> sysMenus = menuService.listByName("");
        // 筛选菜单并返回
        ArrayList<SysMenu> menus = new ArrayList<>();
        for (SysMenu m : sysMenus) {
            // 如果ids包含该id
            if (ids.contains(m.getId())){
                menus.add(m);
            }
            // 获取子菜单
            List<SysMenu> children = m.getChildren();
            // 如果角色的菜单id包含此id则不删除
            children.removeIf(item -> !ids.contains(item.getId()));

        }
        menus.add(0,menus.get(2));
        menus.remove(3);
        return menus;
    }
}

