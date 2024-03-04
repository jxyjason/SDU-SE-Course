package com.example.student_arrangement.controller;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.example.student_arrangement.common.Result;
import com.example.student_arrangement.entity.Game;
import com.example.student_arrangement.mapper.GameMapper;
import com.example.student_arrangement.mapper.PieceMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin


@RequestMapping(value = "/game")
public class GameController {
    @Resource
    GameMapper gameMapper;
    @Resource
    PieceMapper pieceMapper;

    @RequestMapping(value = "/createGame",method = RequestMethod.POST)
    public Result<?> createGame(@RequestBody Game game){
        Game theGame = gameMapper.selectByPlayer12(game.getPlayer1(),game.getPlayer2());
        if(ObjectUtils.isEmpty(theGame)){
            gameMapper.insertGame(game.getPlayer1(),game.getPlayer2(),0);
            return Result.srror("0","开场成功！");
        }else{
            return Result.srror("1","重复开场，开场失败！");
        }
    }

    @RequestMapping(value = "/getAllGame",method = RequestMethod.POST)
    public Game[] getAllGame(@RequestBody Game game){
        return gameMapper.selectAll();
    }

    @RequestMapping(value = "/playWithOthers",method = RequestMethod.POST)
    public Result<?> playWithOthers(@RequestBody Game game){
        if (!game.getPlayer1().equals("")&&!game.getPlayer2().equals("")){
            gameMapper.updateAnotherPlayer(game.getPlayer1(),game.getPlayer2());
            return Result.srror("0","进入！");
        }else{
            return Result.srror("1","进入失败！");
        }
    }

    @RequestMapping(value = "/existRoom",method = RequestMethod.POST)
    public Result<?> existRoom(@RequestBody Game game){
        pieceMapper.deleteByGameId(gameMapper.selectByPlayer1OR2(game.getPlayer1()).getGameId());
        gameMapper.deleteByPlayer12(game.getPlayer1(),game.getPlayer2());
        return Result.success();
    }

    @RequestMapping(value = "/getPlayer",method = RequestMethod.POST)
    public Game getPlayer(@RequestBody Game game){
        return gameMapper.selectByPlayer1OR2(game.getPlayer1());
    }





}
