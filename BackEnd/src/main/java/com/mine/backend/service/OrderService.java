package com.mine.backend.service;

import com.mine.backend.entity.Order;
import com.mine.backend.mapper.OrderMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderService {
    @Resource
    private OrderMapper orderMapper;

    public List<Order> selectAll() {
        return orderMapper.selectAll();
    }

    public List<Order> selectById(Integer id) {
        return orderMapper.selectById(id);
    }

    public void addOrder(Order order) {
        orderMapper.addOrder(order);
    }

    public void updateStatus(Order order) {
        orderMapper.updateStatus(order);
    }

    public List<Order> selectByUsername(String userName) {
        return orderMapper.selectByUsername(userName);
    }

}
