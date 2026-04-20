package com.mine.backend.service;

import com.mine.backend.entity.Apply;
import com.mine.backend.entity.Good;
import com.mine.backend.mapper.ApplyMapper;
import com.mine.backend.mapper.GoodMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@Service
public class ApplyService {
    @Resource
    private ApplyMapper applyMapper;
    @Resource
    private GoodMapper goodMapper;

    public List<Apply> selectAll() {
        return applyMapper.selectAll();
    }

    public List<Apply> selectByApplyName(String applyName) {
        if(applyName.isEmpty()){
            return applyMapper.selectAll();
        }
        return applyMapper.selectByApplyName(applyName);
    }

    public void addApply(Apply apply) {
        LocalDate date = LocalDate.now();
        apply.setDate(String.valueOf(date));
        applyMapper.addApply(apply);
    }

    public void updateStatus(Apply apply) {
        if(apply.getStatus().equals("已通过")){
            Good good = new Good();
            good.setGoodName(apply.getApplyName());
            good.setNum(apply.getNum());
            good.setPrice(apply.getPrice());
            good.setImg(apply.getImg());
            goodMapper.addGood(good);
        }
        applyMapper.updateStatus(apply);
    }

    public List<Apply> selectByApplyUser(String applyUser) {
        return applyMapper.selectByApplyUser(applyUser);
    }


}
