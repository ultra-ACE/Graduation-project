package com.mine.backend.controller;


import cn.hutool.core.util.ObjectUtil;
import com.mine.backend.entity.Admin;
import com.mine.backend.enums.ResultCodeEnum;
import com.mine.backend.exception.Result;
import com.mine.backend.service.AdminService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private AdminService adminService;

    @GetMapping("/selectAll")
    public Result selectAll() {
        List<Admin> admins=adminService.selectAll();
        return Result.success(admins);
    }

    @PutMapping("/updateAdminInformation")
    public Result updateInformation(@RequestBody Admin admin){
        adminService.updateInformation(admin);
        return Result.success(admin);
    }

    @GetMapping("/selectByName")
    public Result selectByName(String name){
        List<Admin> admins= adminService.selectByName(name);
        return Result.success(admins);
    }

    @PostMapping("/addAdmin")
    public Result addAdmin(@RequestBody Admin admin){
        if (ObjectUtil.isEmpty(admin.getUsername()) || ObjectUtil.isEmpty(admin.getPassword())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        adminService.addAdmin(admin);
        return Result.success();
    }


    @DeleteMapping("/deleteByUserName{username}")
    public Result deleteByUsername(@PathVariable String username){
        adminService.deleteByUsername(username);
        return Result.success();
    }

    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody Admin admin){
        adminService.updatePassword(admin);
        return Result.success();
    }
}
