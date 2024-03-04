package com.example.student_arrangement.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("nodes")
@Data
public class Nodes {
    @TableId(type = IdType.AUTO,value = "id")
    private int nodeid;
    private String x;
    private String y;

    public Nodes(int nodeid, String x, String y) {
        this.nodeid = nodeid;
        this.x = x;
        this.y = y;
    }

    public int getNodeid() {
        return nodeid;
    }

    public void setNodeid(int nodeid) {
        this.nodeid = nodeid;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }
}
