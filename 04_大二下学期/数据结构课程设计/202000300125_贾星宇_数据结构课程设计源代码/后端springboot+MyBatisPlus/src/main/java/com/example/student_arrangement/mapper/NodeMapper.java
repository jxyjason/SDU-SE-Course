package com.example.student_arrangement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.student_arrangement.entity.Nodes;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface NodeMapper extends BaseMapper<Nodes> {
//    @Select("select * from `user` where id = #{id}")
//    public User selectUserById(@Param("id")String id);

    @Insert("INSERT INTO `shujvjiegou`.`nodes` (`nodeid`, `x`, `y`) VALUES (#{nodeid}, #{x}, #{y})")
    public void insertNode(@Param("nodeid")int nodeid,@Param("x")String x,@Param("y")String y);

    @Select("SELECT * FROM shujvjiegou.nodes where nodeid=#{nodeid}")
    public Nodes selectById(@Param("nodeid")int nodeid);

    @Select("SELECT * FROM shujvjiegou.nodes")
    public Nodes[] selectAll();

    @Delete("DELETE FROM `shujvjiegou`.`nodes`")
    public void deleteAll();

    @Delete("DELETE FROM `shujvjiegou`.`nodes` where nodeid=#{nodeid}")
    public void deleteById(@Param("nodeid")int nodeid);

    @Select("select count(*) from `shujvjiegou`.`nodes`")
    public int getNodeNums();

}
