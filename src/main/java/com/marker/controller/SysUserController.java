package com.marker.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.marker.common.Constants;
import com.marker.common.Result;
import com.marker.controller.dto.UserDto;
import com.marker.controller.dto.UserPasswordDto;
import com.marker.entity.SysUser;
import com.marker.mapper.SysUserMapper;
import com.marker.service.SysUserService;
import com.marker.utils.TokenUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
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
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService userService;

    @Resource
    private SysUserMapper userMapper;

    @PostMapping("/login")
    public Result login(@RequestBody UserDto user) {
        String username = user.getUsername();
        String password = user.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "参数错误");
        }
        return Result.success(userService.login(user));
    }

    /**
     * 修改密码
     * @param userPasswordDTO
     * @return
     */
    @PostMapping("/password")
    public Result password(@RequestBody UserPasswordDto userPasswordDTO) {
        userPasswordDTO.setPassword(SecureUtil.md5(userPasswordDTO.getPassword()));
        userPasswordDTO.setNewPassword(SecureUtil.md5(userPasswordDTO.getNewPassword()));
        userService.updatePassword(userPasswordDTO);
        return Result.success();
    }

    @PostMapping
    public Result save(@RequestBody SysUser sysUser) {
        return Result.success(userService.save(sysUser));
    }


    @PutMapping
    public Result update(@RequestBody SysUser sysUser) {
        return Result.success(userService.updateById(sysUser));
    }

    @GetMapping("/role/{role}")
    public Result listTeachers(@PathVariable String role) {
        LambdaQueryWrapper<SysUser> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SysUser::getRole,role);
        return Result.success(userService.list(lqw));
    }


    @GetMapping()
    public Result list() {
        return Result.success(userService.list());
    }



    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(userService.removeById(id));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(userService.removeBatchByIds(ids));
    }

    @GetMapping("/username/{username}")
    public Result getInfo(@PathVariable String username) {
        LambdaQueryWrapper<SysUser> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SysUser::getUsername, username);
        return Result.success(userService.getOne(lqw));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer page,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String username,
                           @RequestParam(defaultValue = "") String email,
                           @RequestParam(defaultValue = "") String address
    ) {


        return Result.success(userMapper.findPage(new Page<>(page, pageSize), username, email, address));
    }


    /**
     * 导出接口
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<SysUser> list = userService.list();
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("username", "用户名");
        writer.addHeaderAlias("password", "密码");
        writer.addHeaderAlias("nickname", "昵称");
        writer.addHeaderAlias("email", "邮箱");
        writer.addHeaderAlias("phone", "电话");
        writer.addHeaderAlias("address", "地址");
        writer.addHeaderAlias("createTime", "创建时间");
        writer.addHeaderAlias("avatarUrl", "头像");

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

    }

    /**
     * 342sd`1eaw`
     * excel 导入
     *
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public void imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
//        List<User> list = reader.readAll(User.class);

        // 方式2：忽略表头的中文，直接读取表的内容
        List<List<Object>> list = reader.read(1);
        List<SysUser> users = CollUtil.newArrayList();
        for (List<Object> row : list) {
            SysUser user = new SysUser();
            user.setUsername(row.get(0).toString());
            user.setPassword(row.get(1).toString());
            user.setNickname(row.get(2).toString());
            user.setEmail(row.get(3).toString());
            user.setPhone(row.get(4).toString());
            user.setAddress(row.get(5).toString());
            user.setAvatarUrl(row.get(6).toString());
            users.add(user);
        }

        userService.saveBatch(users);
    }

}

