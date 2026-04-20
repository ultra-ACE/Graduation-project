package com.mine.backend.mapper;

import com.mine.backend.entity.Admin;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {

    @Select("select * from admin where isRoot != 1")
    List<Admin> selectAll();

    @Select("select * from admin where username = #{username}")
    Admin selectByUserName(String username);

    @Select("select * from admin where name = #{name}")
    List<Admin> selectByName(String name);

    @Update("update admin set name = #{name}, phone = #{phone}, email = #{email}, avatar = #{avatar} where username = #{username}")
    void updateInformation(Admin admin);

    @Insert("insert into admin (username, password,isRoot,role) values ( #{username}, #{password},0,'Admin')")
    void insertByUsernameAndPassword(Admin admin);

    @Delete("delete from admin where username = #{username}")
    void deleteByUsername(String username);

    @Update("update admin set password = #{password} where username = #{username}")
    void updatePassword(Admin admin);

}
