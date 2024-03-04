package com.example.student_arrangement.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("game")
@Data
public class Player {
    @TableId(type = IdType.AUTO,value = "id")
    private int playerId;
    private String playerName;
    private int playTime;
    private int winTime;

    @Override
    public String toString() {
        return "Player{" +
                "playerId=" + playerId +
                ", playerName='" + playerName + '\'' +
                ", playTime=" + playTime +
                ", winTime=" + winTime +
                '}';
    }

    public Player(int playerId, String playerName, int playTime, int winTime) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.playTime = playTime;
        this.winTime = winTime;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayTime() {
        return playTime;
    }

    public void setPlayTime(int playTime) {
        this.playTime = playTime;
    }

    public int getWinTime() {
        return winTime;
    }

    public void setWinTime(int winTime) {
        this.winTime = winTime;
    }
}
