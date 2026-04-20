package com.mine.backend.controller;


import com.mine.backend.entity.User;
import com.mine.backend.exception.Result;
import com.mine.backend.mapper.UserMapper;
import com.mine.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/selectAll")
    public Result selectAll() {
        List<User> users = userService.selectAll();
        return Result.success(users);
    }

    @GetMapping("/selectByName")
    public Result selectByName(String name) {
        List<User> users = userService.selectByName(name);
        return Result.success(users);
    }

    @PutMapping("/updateUserPassword")
    public Result updatePassword(@RequestBody User user) {
        userService.updatePassword(user);
        return Result.success();
    }

    @PutMapping("/updateUserInformation")
    public Result updateInformation(@RequestBody User user) {
        userService.updateInformation(user);
        return Result.success();
    }

    @DeleteMapping("/deleteByUserName{username}")
    public Result deleteByUsername( @PathVariable String username){
        userService.deleteByUsername(username);
        return Result.success();
    }





}
