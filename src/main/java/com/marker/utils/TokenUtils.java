package com.marker.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.server.HttpServerRequest;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.marker.entity.SysUser;
import com.marker.service.SysUserService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import springfox.documentation.RequestHandler;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.Resources;
import java.util.Date;

/**
 * token管理工具类
 */
public class TokenUtils {

    private static SysUserService staticUserService;
    @Resource
    private SysUserService userService;

    @PostConstruct
    public void setStaticUserService() {
        staticUserService = userService;
    }


    /**
     * 生成token
     *
     * @param uid  用户id
     * @param sign 密码
     * @return
     */
    public static String token(String uid, String sign) {
        return JWT.create().withAudience(uid)   // 将user id 存到token,作为荷载
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) // 2小时后token过期
                .sign(Algorithm.HMAC256(sign)); // 以 password 作为token的秘钥
    }

    /**
     * 获取当前登录的用户信息
     *
     * @return
     */
    public static SysUser getCurrentUser() {
        try {
            HttpServerRequest request = (HttpServerRequest) ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if (StrUtil.isBlank(token)) {
                String uid = JWT.decode(token).getAudience().get(0);
                return staticUserService.getById(uid);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }


}
