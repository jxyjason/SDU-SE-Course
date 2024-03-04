package com.example.student_arrangement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.student_arrangement.entity.Player;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PlayerMapper extends BaseMapper<Player> {
//    @Insert("INSERT INTO `shujvjiegou`.`nodes` (`nodeid`, `x`, `y`) VALUES (#{nodeid}, #{x}, #{y})")
//    public void insertNode(@Param("nodeid")int nodeid, @Param("x")String x, @Param("y")String y);

    @Select("SELECT * FROM chess.player where playerName = #{playerName}")
    public Player selectByName(@Param("playerName")String playerName);

    @Insert("INSERT INTO `chess`.`player` (`playerName`, `playTime`, `winTime`) VALUES (#{playerName},#{playTime},#{winTime})")
    public void insertPlayer(@Param("playerName")String playerName,@Param("playTime")int playTime,@Param("winTime")int winTime);


}
