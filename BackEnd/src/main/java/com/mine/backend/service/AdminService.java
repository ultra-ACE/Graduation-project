package com.mine.backend.service;


import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.mine.backend.entity.Admin;
import com.mine.backend.enums.ResultCodeEnum;
import com.mine.backend.enums.RoleEnum;
import com.mine.backend.exception.CustomException;
import com.mine.backend.mapper.AdminMapper;
import com.mine.backend.untils.JwtTokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminService {

    @Resource
    private AdminMapper adminMapper;

    public Admin adminLogin(Admin admin) {
        Admin dbadmin = adminMapper.selectByUserName(admin.getUsername());
        if(ObjectUtils.isEmpty(dbadmin)){
            throw new CustomException(ResultCodeEnum.ADMIN_NOT_EXIST_ERROR);
        }
        if(!admin.getPassword().equals(dbadmin.getPassword())){
            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }
        String token= JwtTokenUtils.createToken(dbadmin.getUsername()+"-"+ RoleEnum.Admin.name(), dbadmin.getPassword());
        dbadmin.setToken(token);
        return dbadmin;
    }

    public Admin selectByUserName(String username){
        return adminMapper.selectByUserName(username);
    }

    public List<Admin> selectAll(){
        return adminMapper.selectAll();
    }

    public void updateInformation(Admin admin){
        adminMapper.updateInformation(admin);
    }

    public List<Admin> selectByName(String name){
        if(name.isEmpty()){
            return adminMapper.selectAll();
        }
        return adminMapper.selectByName(name);
    }

    public void addAdmin(Admin admin) {
        Admin dbAdmin = adminMapper.selectByUserName(admin.getUsername());
        if (ObjectUtils.isNotEmpty(dbAdmin)) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        adminMapper.insertByUsernameAndPassword(admin);
    }

    public void deleteByUsername(String username){
        adminMapper.deleteByUsername(username);
    }

    public void updatePassword(Admin admin){
        adminMapper.updatePassword(admin);
    }
}
