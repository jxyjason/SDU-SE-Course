package com.example.student_arrangement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.student_arrangement.entity.Game;
import com.example.student_arrangement.entity.Player;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface GameMapper extends BaseMapper<Game> {
    @Select("SELECT * FROM chess.game where player1 = #{player1} and player2 = #{player2}")
    public Game selectByPlayer12(@Param("player1")String player1,@Param("player2")String player2);

    @Select("SELECT * FROM chess.game where player1 = #{player1} or player2 = #{player1}")
    public Game selectByPlayer1OR2(@Param("player1")String player1);

    @Select("SELECT * FROM chess.game where player1 = #{player1}")
    public Game selectByPlayer1(@Param("player1")String player1);

    @Select("SELECT * FROM chess.game where player2 = #{player2}")
    public Game selectByPlayer2(@Param("player2")String player2);

    @Delete("DELETE FROM `chess`.`game` WHERE (player1 = #{player1} and player2 = #{player2});")
    public void deleteByPlayer12(@Param("player1")String player1,@Param("player2")String player2);

    @Insert("INSERT INTO `chess`.`game` (`player1`, `player2`,`isUsing`) VALUES (#{player1},#{player2},#{isUsing})")
    public void insertGame(@Param("player1")String player1,@Param("player2")String player2,@Param("isUsing")int isUsing);

    @Select("SELECT * FROM chess.game")
    public Game[] selectAll();

    @Update("UPDATE `chess`.`game` SET `player2` = #{player2},`isUsing` = '1'  WHERE (`player1` = #{player1})")
    public void updateAnotherPlayer(@Param("player1")String player1,@Param("player2")String player2);












}
