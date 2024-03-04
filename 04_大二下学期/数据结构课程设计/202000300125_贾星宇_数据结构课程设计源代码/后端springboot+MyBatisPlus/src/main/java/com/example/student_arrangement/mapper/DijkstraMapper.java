package com.example.student_arrangement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.student_arrangement.entity.Dijkstra;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DijkstraMapper extends BaseMapper<Dijkstra> {


    @Insert("INSERT INTO `shujvjiegou`.`dijkstra` (`node`, `distance`, `prenode`,`selected`) VALUES (#{node},#{distance},#{prenode},#{selected})")
    public void insertDij(@Param("node")int node, @Param("distance")String distance, @Param("prenode")String prenode, @Param("selected")String selected);

    @Delete("DELETE FROM shujvjiegou.dijkstra;")
    public void deleteAll();

    @Select("SELECT * FROM shujvjiegou.dijkstra")
    public Dijkstra[] selectAll();

    @Update("UPDATE `shujvjiegou`.`dijkstra` SET `distance` = #{distance}, `prenode` = #{prenode}, `selected` = #{selected} WHERE (`node` = #{theNode})")
    public void updateById(@Param("distance")String distance, @Param("prenode")String prenode,@Param("selected")String selected,@Param("theNode")int theNode);

    @Select("SELECT * FROM shujvjiegou.dijkstra where node = #{node}")
    public Dijkstra selectByNode(@Param("node")int node);

    @Select("SELECT * FROM shujvjiegou.dijkstra where selected = #{selected}")
    public Dijkstra[] selectBySelected(@Param("selected")String selected);

}
