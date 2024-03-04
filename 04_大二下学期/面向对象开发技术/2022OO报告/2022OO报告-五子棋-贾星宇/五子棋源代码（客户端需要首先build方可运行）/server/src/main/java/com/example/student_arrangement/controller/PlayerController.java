package com.example.student_arrangement.controller;


import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.example.student_arrangement.common.Result;
import com.example.student_arrangement.entity.Game;
import com.example.student_arrangement.entity.Player;
import com.example.student_arrangement.mapper.PlayerMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin


@RequestMapping(value = "/player")
public class PlayerController {
    @Resource
    PlayerMapper playerMapper;

    @RequestMapping(value = "/savePlayer",method = RequestMethod.POST)
    public Result<?> savePlayer(@RequestBody Player player){
        Player thePlayer = playerMapper.selectByName(player.getPlayerName());
        if (ObjectUtils.isEmpty(thePlayer)){
            playerMapper.insertPlayer(player.getPlayerName(),player.getPlayTime(),player.getWinTime());
            return Result.srror("0","欢迎你，"+player.getPlayerName()+"。 开始享受五子棋吧~");
        }else {
            return Result.srror("0","欢迎你，"+player.getPlayerName()+"!");
        }
    }

}
