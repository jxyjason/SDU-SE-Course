package com.example.student_arrangement.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("game")
@Data
public class Game {
    @TableId(type = IdType.AUTO,value = "id")
    private int gameId;
    private String player1;
    private String player2;
    private int isUsing;

    public Game(int gameId, String player1, String player2, int isUsing) {
        this.gameId = gameId;
        this.player1 = player1;
        this.player2 = player2;
        this.isUsing = isUsing;
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameId=" + gameId +
                ", player1='" + player1 + '\'' +
                ", player2='" + player2 + '\'' +
                ", isUsing=" + isUsing +
                '}';
    }

    public int getIsUsing() {
        return isUsing;
    }

    public void setIsUsing(int isUsing) {
        this.isUsing = isUsing;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }
}
