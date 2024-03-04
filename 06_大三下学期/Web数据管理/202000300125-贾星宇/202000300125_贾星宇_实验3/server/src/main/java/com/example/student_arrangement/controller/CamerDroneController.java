package com.example.student_arrangement.controller;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.example.student_arrangement.common.Result;
import com.example.student_arrangement.entity.CameraDrone;
import com.example.student_arrangement.entity.CountInfo;
import com.example.student_arrangement.entity.Nodes;
import com.example.student_arrangement.mapper.CamerDroneMapper;
import com.example.student_arrangement.mapper.NodeMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin


@RequestMapping(value = "/cameraDrone")
public class CamerDroneController {
    @Resource
    CamerDroneMapper camerDroneMapper;

    @RequestMapping(value = "/getAllInfo",method = RequestMethod.POST)
    public List<CameraDrone> getAllInfo(@RequestBody CameraDrone cameraDrone){
        List<CameraDrone> cameraDrone1 = camerDroneMapper.selectAll();
        return cameraDrone1;
    }

    @RequestMapping(value = "/getRangeInfo",method = RequestMethod.POST)
    public List<CameraDrone> getRangeInfo(@RequestBody CameraDrone cameraDrone){
           return camerDroneMapper.selectRange(Integer.parseInt(cameraDrone.getDronename()),Integer.parseInt(cameraDrone.getBbscomment()));
    }

    @RequestMapping(value = "/getEachDroneNum",method = RequestMethod.POST)
    public List<CountInfo> getEachDroneNum(){
           return camerDroneMapper.getNumOrderByDroneName();
    }

    @RequestMapping(value = "/getEachUserNum",method = RequestMethod.POST)
    public List<CountInfo> getEachUserNum(){
           return camerDroneMapper.getNumOrderByUserName();
    }


    @RequestMapping(value = "/getByUserName",method = RequestMethod.POST)
    public List<CameraDrone> getByUserName(@RequestBody CameraDrone cameraDrone){
        return camerDroneMapper.selectByUserName(cameraDrone.getBbsusername());
    }

    @RequestMapping(value = "/getByDroneName",method = RequestMethod.POST)
    public List<CameraDrone> getByDroneName(@RequestBody CameraDrone cameraDrone){
        return camerDroneMapper.selectByDroneName(cameraDrone.getDronename());
    }

    @RequestMapping(value = "/getByContent",method = RequestMethod.POST)
    public List<CameraDrone> getByContent(@RequestBody CameraDrone cameraDrone){
        return camerDroneMapper.selectBycontent(cameraDrone.getBbscontent());
    }

    @RequestMapping(value = "/getALLDRONEUSERNums",method = RequestMethod.POST)
    public CameraDrone getALLDRONEUSERNums(@RequestBody CameraDrone cameraDrone){
        CameraDrone cameraDrone1 = new CameraDrone();
        cameraDrone1.setBbsid(camerDroneMapper.getAllNums());
        cameraDrone1.setBbsusername(String.valueOf(camerDroneMapper.getUserNums()));
        cameraDrone1.setDronename(String.valueOf((camerDroneMapper.getDroneNums())));
        return cameraDrone1;
    }





}
