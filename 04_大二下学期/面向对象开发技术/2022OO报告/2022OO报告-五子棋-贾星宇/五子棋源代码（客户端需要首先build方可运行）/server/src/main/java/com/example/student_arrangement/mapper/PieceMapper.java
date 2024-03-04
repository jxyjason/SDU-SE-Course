package com.example.student_arrangement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.student_arrangement.entity.Game;
import com.example.student_arrangement.entity.Piece;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PieceMapper extends BaseMapper<Piece> {
    @Insert("INSERT INTO `chess`.`piece` (`gameId`, `player`, `pX`, `pY`) VALUES (#{gameId}, #{player}, #{pX}, #{pY})")
    public void insertPiece(@Param("gameId")int gameId,@Param("player")String player,@Param("pX")int pX,@Param("pY")int pY);

    @Select("SELECT * FROM chess.piece")
    public Piece[] selectAll();

    @Select("SELECT * FROM chess.piece where gameId = #{gameId}")
    public Piece[] selectByGameId(@Param("gameId")int gameId);

    @Select("SELECT * FROM chess.piece where (`pX` = #{opX} and `pY` = #{opY})")
    public Piece selectByPxy(@Param("opX")int opX,@Param("opY")int opY);

    @Select("SELECT * FROM chess.piece where (`pX` = #{opX} and `pY` = #{opY} and `player` = #{player})")
    public Piece selectByPxyAndPlayer(@Param("opX")int opX,@Param("opY")int opY,@Param("player")String player);

    @Delete("DELETE FROM `chess`.`piece` WHERE (`gameId` = #{gameId})")
    public void deleteByGameId(@Param("gameId")int gameId);

    @Update("UPDATE `chess`.`piece` SET `pX` = #{pX}, `pY` = #{pY} WHERE (pieceId = #{pieceId})")
    public void changePieceById(@Param("pX")int pX,@Param("pY")int pY,@Param("pieceId")int pieceId);

    @Delete("DELETE FROM `chess`.`piece` WHERE (`pX` = #{opX} and `pY` = #{opY})")
    public void deleteByPosition(@Param("opX")int opX,@Param("opY")int opY);

    @Delete("DELETE FROM `chess`.`piece` WHERE (gameId = #{gameId})")
    public void deleteAllPieceById(@Param("gameId")int gameId);

    @Select("SELECT * FROM chess.piece where (`pX` = #{pX} and `player` = #{player})")
    public Piece[] selectByPxAndPlayer(@Param("pX")int pX,@Param("player")String player);

    @Select("SELECT * FROM chess.piece where (`pY` = #{pY} and `player` = #{player})")
    public Piece[] selectByPyAndPlayer(@Param("pY")int pY,@Param("player")String player);









}
