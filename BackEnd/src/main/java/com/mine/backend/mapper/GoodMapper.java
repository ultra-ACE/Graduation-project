package com.mine.backend.mapper;

import com.mine.backend.entity.Good;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GoodMapper {
    @Select("select * from good order by sales desc")
    List<Good> selectAll();

    @Select("select * from good where id = #{id}")
    Good selectById(Integer id);

    @Insert("insert into good (goodName, price, num, img, sales) values ( #{goodName}, #{price}, #{num}, #{img}, 0)")
    void addGood(Good good);

    @Update("update good set num = #{num}  , sales = #{sales}  where id = #{id}")
    void subNum(Good good);

    @Update("update good set goodName = #{goodName}, price = #{price}, num = #{num}, img = #{img} where id = #{id}")
    void updateGood(Good good);

    @Select("select * from good where goodName = #{goodName}")
    List<Good> selectByGoodName(String goodName);

    @Delete("delete from good where id = #{id}")
    void deleteById(Integer id);


}
