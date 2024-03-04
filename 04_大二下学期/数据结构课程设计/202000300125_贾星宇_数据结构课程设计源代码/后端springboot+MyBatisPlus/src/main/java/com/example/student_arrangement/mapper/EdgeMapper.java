package com.example.student_arrangement.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.student_arrangement.entity.Edges;
import com.example.student_arrangement.entity.Nodes;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface EdgeMapper extends BaseMapper<Edges> {
    @Insert("INSERT INTO `shujvjiegou`.`edges` (`begnode`, `endnode`, `weight`) VALUES (#{begnode},#{endnode},#{weight})")
    public void addEdge(@Param("begnode")int begnode,@Param("endnode")int endnode,@Param("weight")int weight);

    @Select("SELECT * FROM shujvjiegou.edges where begnode = #{begnode} and endnode = #{endnode}")
    public Edges selectEdgeByNode12(@Param("begnode")int begnode, @Param("endnode")int endnode);

    @Select("SELECT * FROM shujvjiegou.edges")
    public Edges[] getAllEdges();

    @Delete("DELETE FROM `shujvjiegou`.`edges` WHERE (`begnode` = #{begnode} or `endnode` = #{endnode})")
    public void deleteEdgesByNode(@Param("begnode")int begnode,@Param("endnode")int endnode);

    @Delete("DELETE FROM `shujvjiegou`.`edges`")
    public void deleteAll();

    @Delete("DELETE FROM shujvjiegou.edges ORDER BY edgesId DESC LIMIT 1")
    public void deleteLast();

    @Select("SELECT * FROM shujvjiegou.edges where begnode = #{begnode}")
    public Edges[] selectEdgeByBegNode(@Param("begnode")int begnode);

}
