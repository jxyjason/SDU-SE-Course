package com.example.student_arrangement.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("bbscameradrone")
@Data
public class CameraDrone {
    @TableId(type = IdType.AUTO,value = "id")
    private int bbsid;
    private String dronename;
    private String bbscontent;
    private String bbsusername;
    private String bbscomment;
    private String bbspublishtime;
    private String linkdronename;
    private String linkcontent;
    private String linkuser;
    private String linkcomment;

    public int getBbsid() {
        return bbsid;
    }

    public void setBbsid(int bbsid) {
        this.bbsid = bbsid;
    }

    public String getDronename() {
        return dronename;
    }

    public void setDronename(String dronename) {
        this.dronename = dronename;
    }

    public String getBbscontent() {
        return bbscontent;
    }

    public void setBbscontent(String bbscontent) {
        this.bbscontent = bbscontent;
    }

    public String getBbsusername() {
        return bbsusername;
    }

    public void setBbsusername(String bbsusername) {
        this.bbsusername = bbsusername;
    }

    public String getBbscomment() {
        return bbscomment;
    }

    public void setBbscomment(String bbscomment) {
        this.bbscomment = bbscomment;
    }

    public String getBbspublishtime() {
        return bbspublishtime;
    }

    public void setBbspublishtime(String bbspublishtime) {
        this.bbspublishtime = bbspublishtime;
    }

    public String getLinkdronename() {
        return linkdronename;
    }

    public void setLinkdronename(String linkdronename) {
        this.linkdronename = linkdronename;
    }

    public String getLinkcontent() {
        return linkcontent;
    }

    public void setLinkcontent(String linkcontent) {
        this.linkcontent = linkcontent;
    }

    public String getLinkuser() {
        return linkuser;
    }

    public void setLinkuser(String linkuser) {
        this.linkuser = linkuser;
    }

    public String getLinkcomment() {
        return linkcomment;
    }

    public void setLinkcomment(String linkcomment) {
        this.linkcomment = linkcomment;
    }
}
