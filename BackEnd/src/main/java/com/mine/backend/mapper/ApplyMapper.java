package com.mine.backend.mapper;

import com.mine.backend.entity.Apply;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ApplyMapper {

    @Select("select * from apply")
    List<Apply> selectAll();

    @Select("select * from apply where applyName = #{applyName}")
    List<Apply> selectByApplyName(String applyName);

    @Select("select * from apply where applyUser = #{applyUser}")
    List<Apply> selectByApplyUser(String applyUser);

    @Insert("insert into apply (applyName, date, price, num, img, status, applyUser) values (#{applyName},#{date},#{price},#{num},#{img},'待审批',#{applyUser})")
    void addApply(Apply apply);

    @Update("update apply set status = #{status} where id = #{id}")
    void updateStatus(Apply apply);

}
