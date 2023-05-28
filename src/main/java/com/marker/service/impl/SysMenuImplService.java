package com.marker.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.marker.entity.SysMenu;
import com.marker.mapper.SysMenuMapper;
import com.marker.service.SysMenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author YSZ
 * @since 2023-05-15
 */
@Service
public class SysMenuImplService extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Override
    public List<SysMenu> listByName(String name) {
        LambdaQueryWrapper<SysMenu> lqw = new LambdaQueryWrapper<>();
        if(StrUtil.isBlank(name)){
            lqw.like(SysMenu::getName, name);
        }
        lqw.orderByAsc(SysMenu::getId);
        // 查询所有数据
        List<SysMenu> list = list(lqw);
        // 找出一级菜单   如果没有pid 就为一级菜单
        List<SysMenu> collects = list.stream().filter(item -> item.getPid() == null).collect(Collectors.toList());
        // 找出一级菜单的子菜单
        for (SysMenu menu : collects) {
            menu.setChildren(list.stream().filter(item -> Objects.equals(item.getPid(), menu.getId())).collect(Collectors.toList()));
        }

        return collects;
    }
}
