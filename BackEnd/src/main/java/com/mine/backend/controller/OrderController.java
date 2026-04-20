package com.mine.backend.controller;


import com.mine.backend.entity.Order;
import com.mine.backend.exception.Result;
import com.mine.backend.service.OrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService orderService;

    @GetMapping("/selectAll")
    public Result selectAll() {
        List<Order> orders = orderService.selectAll();
        return Result.success(orders);
    }

    @GetMapping("/selectById")
    public Result selectById(String id) {
        if(id.isEmpty()){
            List<Order> orders = orderService.selectAll();
            return Result.success(orders);
        }
        List<Order> orders = orderService.selectById(Integer.parseInt(id));
        return Result.success(orders);
    }

    @PostMapping("/addOrder")
    public Result addOrder(@RequestBody Order order) {
        orderService.addOrder(order);
        return Result.success();
    }

    @PutMapping("/updateStatus")
    public Result updateStatus(@RequestBody Order order) {
        orderService.updateStatus(order);
        return Result.success();
    }

    @GetMapping("/selectByUsername")
    public Result selectByUsername(String userName) {
        List<Order> orders = orderService.selectByUsername(userName);
        return Result.success(orders);
    }


}
