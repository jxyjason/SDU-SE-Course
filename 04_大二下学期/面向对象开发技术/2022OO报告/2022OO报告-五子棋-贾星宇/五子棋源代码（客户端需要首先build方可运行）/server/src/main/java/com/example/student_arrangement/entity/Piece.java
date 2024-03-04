package com.example.student_arrangement.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("game")
@Data
public class Piece {
    @TableId(type = IdType.AUTO,value = "id")
    private int pieceId;
    private int gameId;
    private String player;
    private int pX;
    private int pY;

    public Piece(int pieceId, int gameId, String player, int pX, int pY) {
        this.pieceId = pieceId;
        this.gameId = gameId;
        this.player = player;
        this.pX = pX;
        this.pY = pY;
    }

    public int getPieceId() {
        return pieceId;
    }

    public void setPieceId(int pieceId) {
        this.pieceId = pieceId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getpX() {
        return pX;
    }

    public void setpX(int pX) {
        this.pX = pX;
    }

    public int getpY() {
        return pY;
    }

    public void setpY(int pY) {
        this.pY = pY;
    }
}
