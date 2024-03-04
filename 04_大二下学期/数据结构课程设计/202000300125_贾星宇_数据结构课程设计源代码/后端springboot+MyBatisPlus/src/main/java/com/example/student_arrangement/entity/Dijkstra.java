package com.example.student_arrangement.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("dijkstra")
@Data
public class Dijkstra {
    @TableId(type = IdType.AUTO,value = "id")
    private int node;
    private String distance;
    private String prenode;
    private String selected;

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public Dijkstra(int node, String distance, String prenode, String selected) {
        this.node = node;
        this.distance = distance;
        this.prenode = prenode;
        this.selected = selected;
    }

    public int getNode() {
        return node;
    }

    public void setNode(int node) {
        this.node = node;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPrenode() {
        return prenode;
    }

    public void setPrenode(String prenode) {
        this.prenode = prenode;
    }
}
