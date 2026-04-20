package com.mine.backend.mapper;

import com.mine.backend.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface OrderMapper {

    @Select("select * from orders")
    List<Order> selectAll();

    @Insert("insert into orders (goodId, userId, goodName, userName, date, price, num, img, status, address) values (#{goodId},#{userId},#{goodName},#{userName},#{date},#{price},#{num},#{img},'待发货',#{address})")
    void addOrder(Order order);

    @Select("select * from orders where userName = #{userName}")
    List<Order> selectByUsername(String userName);

    @Update("update orders set status = #{status} where id = #{id}")
    void updateStatus(Order order);

    @Select("select * from orders where id = #{id}")
    List<Order> selectById(Integer id);


}
