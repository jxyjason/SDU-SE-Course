package com.example.student_arrangement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.student_arrangement.entity.CameraDrone;
import com.example.student_arrangement.entity.CountInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CamerDroneMapper extends BaseMapper<CameraDrone> {
    @Select("SELECT * FROM webshujuguanli.bbscameradrone;")
    public List<CameraDrone> selectAll();

    @Select("SELECT * FROM webshujuguanli.bbscameradrone where bbsid >= #{begin} and bbsid <= #{end};")
    public List<CameraDrone> selectRange(@Param("begin")int begin, @Param("end")int end);

    @Select("SELECT dronename,count(dronename) s FROM webshujuguanli.bbscameradrone group by dronename order by count(dronename) desc;")
    public List<CountInfo> getNumOrderByDroneName();

    @Select("SELECT bbsusername,count(bbsusername) s FROM webshujuguanli.bbscameradrone group by bbsusername order by count(bbsusername) desc;")
    public List<CountInfo> getNumOrderByUserName();

    @Select("SELECT * FROM webshujuguanli.bbscameradrone where bbsusername like CONCAT('%',#{bbsusername},'%');")
    public List<CameraDrone> selectByUserName(@Param("bbsusername")String bbsusername);

    @Select("SELECT * FROM webshujuguanli.bbscameradrone where dronename like CONCAT('%',#{dronename},'%');")
    public List<CameraDrone> selectByDroneName(@Param("dronename")String dronename);

    @Select("SELECT * FROM webshujuguanli.bbscameradrone where bbscontent like CONCAT('%',#{bbscontent},'%');")
    public List<CameraDrone> selectBycontent(@Param("bbscontent")String bbscontent);

    @Select("SELECT count(*) FROM webshujuguanli.bbscameradrone;")
    public int getAllNums();

    @Select("select count(*) from(SELECT bbsusername FROM webshujuguanli.bbscameradrone group by bbsusername)s;")
    public int getUserNums();

    @Select("select count(*) from(SELECT dronename FROM webshujuguanli.bbscameradrone group by dronename)s;")
    public int getDroneNums();








}
