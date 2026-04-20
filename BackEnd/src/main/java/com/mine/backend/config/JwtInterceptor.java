package com.mine.backend.config;

import cn.hutool.core.util.ObjectUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.mine.backend.entity.Account;
import com.mine.backend.enums.ResultCodeEnum;
import com.mine.backend.enums.RoleEnum;
import com.mine.backend.exception.CustomException;
import com.mine.backend.service.AdminService;
import com.mine.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    private final static Logger log = LoggerFactory.getLogger(JwtInterceptor.class);
    @Resource
    private AdminService adminService;
    @Resource
    private UserService userService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token=request.getHeader("token");
        if(ObjectUtil.isEmpty(token)){
            token=request.getParameter("token");
        }
        if(ObjectUtil.isEmpty(token)){
            throw new CustomException(ResultCodeEnum.TOKEN_INVALID_ERROR);
        }
        Account account;
        try{
            String detoken= JWT.decode(token).getAudience().get(0);
            String username=detoken.split("-")[0];
            String role=detoken.split("-")[1];

            if(RoleEnum.Admin.name().equals(role)){
                account=adminService.selectByUserName(username);
            }
            else{
                account=userService.selectByUserName(username);
            }
        }
        catch (Exception e){
            throw new CustomException(ResultCodeEnum.TOKEN_CHECK_ERROR);
        }
        if(ObjectUtil.isEmpty(account)){
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        try{
            JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256(account.getPassword())).build();
            jwtVerifier.verify(token);
        }
        catch (JWTVerificationException e){
            throw new CustomException(ResultCodeEnum.TOKEN_CHECK_ERROR);
        }
        return true;
    }


}
