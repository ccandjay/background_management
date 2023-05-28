package com.marker.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.marker.common.Result;
import com.marker.entity.SysRole;
import com.marker.entity.SysRoleMenu;
import com.marker.service.SysRoleMenuService;
import com.marker.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author YSZ
 * @since 2023-05-15
 */
@RestController
@RequestMapping("/role")
public class SysRoleController {

    @Autowired
    private SysRoleService roleService;

    @Resource
    private SysRoleMenuService roleMenuService;

    @PostMapping
    public Result saveOrUpdate(@RequestBody SysRole sysRole) {
        roleService.saveOrUpdate(sysRole);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        roleService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        roleService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(roleService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(roleService.getById(id));
    }


    @GetMapping("/page")
    public Result page(@RequestParam String name,
                       @RequestParam Integer pageNum,
                       @RequestParam Integer pageSize) {
        LambdaQueryWrapper<SysRole> lqw = new LambdaQueryWrapper<>();
        lqw.like(SysRole::getName, name);
        lqw.orderByDesc(SysRole::getId);
        return Result.success(roleService.page(new Page<>(pageNum, pageSize), lqw));
    }

    /**
     * 绑定角色和菜单的关系
     *
     * @param roleId  角色id
     * @param ids 菜单id字符串
     * @return
     */
    @PostMapping("/roleMenu")
    public Result roleMenu(@RequestParam Integer roleId, @RequestParam String ids) {
        roleService.setRoleMenu(roleId, ids);
        return Result.success();
    }


    /**
     * 获取角色已经分配的菜单id
     * @param roleId 角色id
     * @return 菜单id集合
     */
    @GetMapping("/roleMenu")
    public Result getRoleMenu(@RequestParam Integer roleId) {
        LambdaQueryWrapper<SysRoleMenu> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SysRoleMenu::getRoleId, roleId);
        List<SysRoleMenu> list = roleMenuService.list(lqw);
        return Result.success(list);
    }
}

