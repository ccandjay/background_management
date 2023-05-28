package com.marker.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.marker.common.Constants;
import com.marker.common.Result;
import com.marker.entity.SysDict;
import com.marker.entity.SysMenu;
import com.marker.service.SysDictService;
import com.marker.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/menu")
public class SysMenuController {

    @Autowired
    private SysMenuService menuService;

    @Autowired
    private SysDictService dictService;

    @PostMapping
    public Result saveOrUpdate(@RequestBody SysMenu menu) {
        menuService.saveOrUpdate(menu);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        menuService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        menuService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll(@RequestParam String name) {
        List<SysMenu> sysMenus = menuService.listByName(name);
        return Result.success(sysMenus);
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(menuService.getById(id));
    }


    @GetMapping("/page")
    public Result page(@RequestParam String name,
                       @RequestParam Integer pageNum,
                       @RequestParam Integer pageSize) {
        LambdaQueryWrapper<SysMenu> lqw = new LambdaQueryWrapper<>();
        lqw.like(SysMenu::getName, name);
        lqw.orderByDesc(SysMenu::getId);
        return Result.success(menuService.page(new Page<>(pageNum, pageSize), lqw));
    }

    @GetMapping("/icons")
    public Result getIcons(){
        LambdaQueryWrapper<SysDict> lqw = new LambdaQueryWrapper<>();;
        lqw.eq(SysDict::getType, Constants.DICT_TYPE_ICON);
        return Result.success(dictService.list(lqw));
    }
}

