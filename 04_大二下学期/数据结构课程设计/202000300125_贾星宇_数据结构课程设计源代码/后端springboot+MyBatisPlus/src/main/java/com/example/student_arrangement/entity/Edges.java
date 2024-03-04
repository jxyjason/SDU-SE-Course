package com.example.student_arrangement.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("edges")
@Data
public class Edges {
    @TableId(type = IdType.AUTO,value = "id")
    private int edgesId;
    private int begnode;
    private int endnode;
    private int weight;

    public Edges(int edgesId, int begnode, int endnode, int weight) {
        this.edgesId = edgesId;
        this.begnode = begnode;
        this.endnode = endnode;
        this.weight = weight;
    }



    public int getEdgesId() {
        return edgesId;
    }

    public void setEdgesId(int edgesId) {
        this.edgesId = edgesId;
    }

    public int getBegnode() {
        return begnode;
    }

    public void setBegnode(int begnode) {
        this.begnode = begnode;
    }

    public int getEndnode() {
        return endnode;
    }

    public void setEndnode(int endnode) {
        this.endnode = endnode;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
