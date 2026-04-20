package com.mine.backend.service;

import com.mine.backend.entity.Good;
import com.mine.backend.mapper.GoodMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GoodService {
    @Resource
    private GoodMapper goodMapper;

    public List<Good> selectAll() {
        return goodMapper.selectAll();
    }

    public List<Good> selectByGoodName(String goodname){
        if (goodname.isEmpty())
            return goodMapper.selectAll();
        return goodMapper.selectByGoodName(goodname);
    }

    public void addGood(Good good){
        goodMapper.addGood(good);
    }

    public void sale(Good good){
        goodMapper.subNum(good);
    }

    public void updateGood(Good good){
        goodMapper.updateGood(good);
    }

    public void deleteGood(int id){
        goodMapper.deleteById(id);
    }

    public void deleteGoods(List<Good> goods){
        for(Good good:goods){
            goodMapper.deleteById(good.getId());
        }
    }

    public Good selectById(Integer id){
        return goodMapper.selectById(id);
    }




}
