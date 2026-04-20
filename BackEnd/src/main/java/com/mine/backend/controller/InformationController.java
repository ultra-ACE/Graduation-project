package com.mine.backend.controller;


import com.mine.backend.entity.Information;
import com.mine.backend.exception.Result;
import com.mine.backend.service.InformationService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/information")
public class InformationController {
    @Resource
    private InformationService informationService;

    @GetMapping("/selectAll")
    public Result selectAll() {
        List<Information> informationList = informationService.selectAll();
        return Result.success(informationList);
    }

    @GetMapping("/selectByTitle")
    public Result selectByTitle(String title) {
        List<Information> informationList = informationService.selectByTitle(title);
        return Result.success(informationList);
    }

    @PostMapping("/addInformation")
    public Result addInformation(@RequestBody Information information) {
        informationService.addInformation(information);
        return Result.success();
    }

    @PutMapping("/updateInformation")
    public Result updateInformation(@RequestBody Information information) {
        informationService.updateInformation(information);
        return Result.success();
    }

    @DeleteMapping("/deleteInformation{id}")
    public Result deleteInformation(@PathVariable String id) {
        informationService.deleteInformation(Integer.parseInt(id));
        return Result.success();
    }

    @PutMapping("/deleteInformations")
    public Result deleteInformations(@RequestBody List<Information> informations) {
        informationService.deleteInformations(informations);
        return Result.success();
    }
}
