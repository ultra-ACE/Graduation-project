package com.mine.backend.mapper;



import com.mine.backend.entity.User;
import org.apache.ibatis.annotations.*;


import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from user")
    List<User> selectAll();

    @Select("select * from user where id = #{id}")
    List<User> selectById(Integer id);

    @Select("select * from user where username = #{username}")
    User selectByUserName(String username);

    @Select("select * from user where name = #{name}")
    List<User> selectByName(String name);

    @Insert("insert into user (username, password, name, phone, email, avatar,role) values ( #{username}, #{password}, #{name}, #{phone}, #{email}, #{avatar},'User')")
    void insert(User user);

    @Insert("insert into user (username, password,role) values ( #{username}, #{password},'User')")
    void insertByUsernameAndPassword(User user);

    @Update("update user set password = #{password} where username = #{username}")
    void updatePassword(User user);

    @Update("update user set name = #{name}, phone = #{phone}, email = #{email}, avatar = #{avatar} where username = #{username}")
    void updateInformation(User user);

    @Delete("delete from user where username = #{username}")
    void deleteByUsername(String username);







}
