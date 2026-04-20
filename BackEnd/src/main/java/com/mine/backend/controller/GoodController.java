package com.mine.backend.controller;

import com.mine.backend.entity.Good;
import com.mine.backend.exception.Result;
import com.mine.backend.service.GoodService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/good")
@RestController
public class GoodController {
    @Resource
    private GoodService goodService;

    @GetMapping("/selectAll")
    public Result selectAll(){
        List<Good> goods=goodService.selectAll();
        return Result.success(goods);
    }

    @GetMapping("/selectById")
    public Result selectById(Integer id){
        Good good=goodService.selectById(id);
        return Result.success(good);
    }

    @GetMapping("/selectByGoodName")
    public Result selectByGoodName(String goodname){
        List<Good> goods=goodService.selectByGoodName(goodname);
        return Result.success(goods);
    }

    @DeleteMapping("/deleteGood{id}")
    public Result deleteGood(@PathVariable String id){
        goodService.deleteGood(Integer.parseInt(id));
        return Result.success();
    }

    @PutMapping("/deleteGoods")
    public Result deleteGoods(@RequestBody List<Good> goods){
        goodService.deleteGoods(goods);
        return Result.success(goods);
    }

    @PostMapping("/addGood")
    public Result addGood(@RequestBody Good good){
        goodService.addGood(good);
        return Result.success();
    }

    @PutMapping("/updateGood")
    public Result updateGood(@RequestBody Good good){
        goodService.updateGood(good);
        return Result.success();
    }



}
