package com.mine.backend.controller;

import com.mine.backend.entity.Apply;
import com.mine.backend.exception.Result;
import com.mine.backend.service.ApplyService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/apply")
public class ApplyController {
    @Resource
    private ApplyService applyService;

    @GetMapping("/selectAll")
    public Result selectAll() {
        List<Apply> applies= applyService.selectAll();
        return Result.success(applies);
    }

    @GetMapping("/selectByApplyName")
    public Result selectByApplyName(String applyName) {
        List<Apply> applies = applyService.selectByApplyName(applyName);
        return Result.success(applies);
    }

    @PostMapping("/addApply")
    public Result addApply(@RequestBody Apply apply) {
        applyService.addApply(apply);
        return Result.success();
    }

    @PutMapping("/updateStatus")
    public Result updateApply(@RequestBody Apply apply){
        applyService.updateStatus(apply);
        return Result.success();
    }

    @GetMapping("/selectByApplyUser")
    public Result selectByApplyUser(String applyUser) {
        List<Apply> applies = applyService.selectByApplyUser(applyUser);
        return Result.success(applies);
    }



}
