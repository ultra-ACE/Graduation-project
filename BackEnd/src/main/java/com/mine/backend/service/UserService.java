package com.mine.backend.service;


import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.mine.backend.entity.Account;
import com.mine.backend.entity.User;
import com.mine.backend.enums.ResultCodeEnum;
import com.mine.backend.enums.RoleEnum;
import com.mine.backend.exception.CustomException;
import com.mine.backend.exception.Result;
import com.mine.backend.mapper.UserMapper;
import com.mine.backend.untils.JwtTokenUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService  {

    @Resource
    private UserMapper userMapper;

    public User userLogin(User user) {
        User dbuser = userMapper.selectByUserName(user.getUsername());
        if(ObjectUtils.isEmpty(dbuser)){
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if(!user.getPassword().equals(dbuser.getPassword())){
            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }
        String token=JwtTokenUtils.createToken(dbuser.getUsername()+"-"+RoleEnum.User.name(),
                dbuser.getPassword());
        dbuser.setToken(token);
        return dbuser;
    }

    //用户名查询
    public User selectByUserName(String username) {
        return userMapper.selectByUserName(username);
    }


    //注册
    public void register(User user) {
        User dbUser = userMapper.selectByUserName(user.getUsername());
        if (ObjectUtils.isNotEmpty(dbUser)) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        userMapper.insertByUsernameAndPassword(user);
    }

    //修改密码
    public void updatePassword(User user){
        User dbuser = userMapper.selectByUserName(user.getUsername());
        if(ObjectUtils.isEmpty(dbuser)){
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        userMapper.updatePassword(user);
    }

    //按昵称查询所有用户
    public List<User> selectByName(String name){
        if(name.isEmpty()){
            return userMapper.selectAll();
        }
        return userMapper.selectByName(name);
    }

    public List<User> selectAll(){
        return userMapper.selectAll();
    }


    public void updateInformation(User user) {
        userMapper.updateInformation(user);
    }

    public void deleteByUsername(String username){
        userMapper.deleteByUsername(username);
    }
}
