package com.example.student_arrangement.controller;


import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.example.student_arrangement.common.Result;
import com.example.student_arrangement.entity.Nodes;
import com.example.student_arrangement.mapper.NodeMapper;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;



@RestController
@CrossOrigin


@RequestMapping(value = "/node")
public class NodeController {
    @Resource
    NodeMapper nodeMapper;


    @RequestMapping(value = "/saveNode",method = RequestMethod.POST)
    public Result<?> save(@RequestBody Nodes nodes){
        Nodes node1 = nodeMapper.selectById(nodes.getNodeid());
        if (ObjectUtils.isEmpty(node1)){
            nodeMapper.insertNode(nodes.getNodeid(),nodes.getX(),nodes.getY());
            return Result.srror("0","success");
        }else{
            return Result.srror("1","existNode!");
        }
    }





}
