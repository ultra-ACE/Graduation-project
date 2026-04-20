package com.mine.backend.mapper;

import com.mine.backend.entity.Information;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface InformationMapper {


    @Select("select * from information")
    List<Information> selectAll();

    @Select("select * from information where title = #{title}")
    List<Information> selectByTitle(String title);

    @Insert("insert into information (title, content, date) values ( #{title}, #{content}, #{date})")
    void addInformation(Information information);

    @Delete("delete from information where id= #{id}")
    void deleteInformation(Integer id);

    @Update("update information set title = #{title}, content = #{content}, date = #{date} where id = #{id}")
    void updateInformation(Information information);




}
