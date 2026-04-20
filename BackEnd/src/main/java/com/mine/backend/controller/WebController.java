package com.mine.backend.controller;

import cn.hutool.core.util.ObjectUtil;
import com.mine.backend.entity.Admin;
import com.mine.backend.entity.User;
import com.mine.backend.enums.ResultCodeEnum;
import com.mine.backend.exception.Result;
import com.mine.backend.service.AdminService;
import com.mine.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class WebController {
    @Resource
    private UserService userService;
    @Resource
    private AdminService adminService;

    @PostMapping("/LoginUser")
    public Result LoginUser(@RequestBody User user) {
        if (ObjectUtil.isEmpty(user.getUsername()) || ObjectUtil.isEmpty(user.getPassword())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        user = userService.userLogin(user);
        return Result.success(user);
    }

    @PostMapping("/LoginAdmin")
    public Result LoginAdmin(@RequestBody Admin admin) {
        if (ObjectUtil.isEmpty(admin.getUsername()) || ObjectUtil.isEmpty(admin.getPassword())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        admin = adminService.adminLogin(admin);
        return Result.success(admin);
    }

    @PostMapping("/RegisterUser")
    public Result RegisterUser(@RequestBody User user) {
        if (ObjectUtil.isEmpty(user.getUsername()) || ObjectUtil.isEmpty(user.getPassword())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        userService.register(user);
        return Result.success();
    }

    @PutMapping("/RetrievePassword")
    public Result RetrievePassword(@RequestBody User user) {
        if (ObjectUtil.isEmpty(user.getUsername()) || ObjectUtil.isEmpty(user.getPassword())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        userService.updatePassword(user);
        return Result.success();
    }



}
