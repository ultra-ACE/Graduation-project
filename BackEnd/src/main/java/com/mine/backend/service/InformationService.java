package com.mine.backend.service;

import com.mine.backend.entity.Information;
import com.mine.backend.mapper.InformationMapper;
import org.springframework.stereotype.Service;
import java.io.IOException;

import javax.annotation.Resource;
import java.util.List;

@Service
public class InformationService {
    @Resource
    private InformationMapper informationMapper;

    public List<Information> selectAll() {
        return informationMapper.selectAll();
    }

    public List<Information> selectByTitle(String title){
        if (title.isEmpty())
            return informationMapper.selectAll();
        return informationMapper.selectByTitle(title);
    }

    public void addInformation(Information information){
        informationMapper.addInformation(information);
    }

    public void updateInformation(Information information){
        informationMapper.updateInformation(information);
    }

    public void deleteInformation(Integer id){
        informationMapper.deleteInformation(id);
    }

    public void deleteInformations(List<Information> informations){
        for (Information information : informations) {
            informationMapper.deleteInformation(information.getId());
        }
    }

}
