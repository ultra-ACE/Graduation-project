package com.mine.backend.untils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.mine.backend.service.AdminService;
import com.mine.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;

@Component
public class JwtTokenUtils {

    private static AdminService staticAdminService;
    private static UserService staticUserService;
    private static final Logger log= LoggerFactory.getLogger(JwtTokenUtils.class);
    @Resource
    private AdminService adminService;
    @Resource
    private UserService userService;

    @PostConstruct
    public void setAdminService(){
        staticAdminService=adminService;
    }
    @PostConstruct
    public void setUserService(){
        staticUserService=userService;
    }


    public static String createToken(String username, String password){
        return JWT.create().withAudience(username)  //把username作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 3)) //过期时间3小时
                .sign(Algorithm.HMAC256(password));  //以password作为密钥
    }

}
