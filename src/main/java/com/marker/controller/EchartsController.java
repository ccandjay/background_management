package com.marker.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import cn.hutool.core.io.unit.DataUnit;
import com.marker.common.Result;
import com.marker.entity.SysUser;
import com.marker.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Autowired
    private SysUserService userService;


    @GetMapping("/members")
    public Result get() {
        List<SysUser> userList = userService.list();
        int q1 = 0;     // 第一季度
        int q2 = 0;     // 第二季度
        int q3 = 0;     // 第三季度
        int q4 = 0;     // 第四季度
        Map<String, Object> map = new HashMap<>();
        for (SysUser user : userList) {
            Date createTime = user.getCreateTime();
            // 将创建时间转为季度
            Quarter quarter = DateUtil.quarterEnum(createTime);
            // 统计每个季度的个数量
            switch (quarter) {
                case Q1:
                    q1 += 1;
                    break;
                case Q2:
                    q2 += 1;
                    break;
                case Q3:
                    q3 += 1;
                    break;
                case Q4:
                    q4 += 1;
                    break;
                default:
                    break;
            }
        }
        return Result.success(CollUtil.newArrayList(q1,q2,q3,q4));
    }
}
