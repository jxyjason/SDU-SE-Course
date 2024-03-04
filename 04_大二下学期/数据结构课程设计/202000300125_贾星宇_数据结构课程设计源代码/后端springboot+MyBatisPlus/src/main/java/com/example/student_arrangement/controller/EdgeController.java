package com.example.student_arrangement.controller;


import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.example.student_arrangement.common.Result;
import com.example.student_arrangement.entity.Edges;
import com.example.student_arrangement.entity.Nodes;
import com.example.student_arrangement.mapper.EdgeMapper;
import com.example.student_arrangement.mapper.NodeMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Random;

@RestController
@CrossOrigin


@RequestMapping(value = "/edge")
public class EdgeController {
    @Resource
    EdgeMapper edgeMapper;
    @Resource
    NodeMapper nodeMapper;


    @RequestMapping(value = "/saveEdge",method = RequestMethod.POST)
    public Result<?> save(@RequestBody Edges edges){
        Edges edge1 = edgeMapper.selectEdgeByNode12(edges.getBegnode(),edges.getEndnode());
        Nodes nodes1 = nodeMapper.selectById(edges.getBegnode());
        Nodes nodes2 = nodeMapper.selectById(edges.getEndnode());
        if (ObjectUtils.isEmpty(edge1)){
            if (!ObjectUtils.isEmpty(nodes1)&&!ObjectUtils.isEmpty(nodes2)) {
                edgeMapper.addEdge(edges.getBegnode(), edges.getEndnode(), edges.getWeight());
                return Result.srror("0", "success");
            }else {
                return Result.srror("2","no nodes!");
            }
        }else{
            return Result.srror("1","existEdges!");
        }
    }

    @RequestMapping(value = "/getEdges",method = RequestMethod.POST)
    public Edges[] getAllEdges(@RequestBody Edges edges){
        return edgeMapper.getAllEdges();
    }

    @RequestMapping(value = "/lastPace",method = RequestMethod.POST)
    public Result<?> lastPace(@RequestBody Edges edges){
        Edges edges1[] = edgeMapper.getAllEdges();
        if (edges1.length==0)return Result.srror("1","no data");
        else {
            edgeMapper.deleteLast();
            return Result.srror("0",String.valueOf(edges1.length-1));
        }
    }

    //begnode:点个数；endnode:边个数
    @RequestMapping(value = "/autoCreate",method = RequestMethod.POST)
    public Result<?> autoCreate(@RequestBody Edges edges){
        if (nodeMapper.selectAll().length!=0) {
            nodeMapper.deleteAll();
            edgeMapper.deleteAll();
        }
            int nNum = edges.getBegnode();
            int eNum = edges.getEndnode();
            for (int i = 0; i < nNum; i++) {
                String x = String.valueOf(new Random().nextInt(76) + 5)+"%";
                String y = String.valueOf(new Random().nextInt(76) + 5)+"%";
                nodeMapper.insertNode(i, x, y);
            }

            for (int i = 0; i < eNum;) {
                int begNode = new Random().nextInt(nNum);
                int endNode = new Random().nextInt(nNum);
                if (begNode==endNode|| !ObjectUtils.isEmpty(edgeMapper.selectEdgeByNode12(begNode,endNode)))continue;

                int weight = new Random().nextInt(20);
                edgeMapper.addEdge(begNode, endNode, weight);
                i++;
            }
        return Result.success();
    }


    @RequestMapping(value = "/getIsEdgeByBegAndEnd",method = RequestMethod.POST)
    public int[][] getIsEdgeByBegAndEnd(@RequestBody Edges edges){
        int nodeNum = nodeMapper.getNodeNums();
        int [][] theVertex = new int[nodeNum][nodeNum];
        for (int i=0;i<nodeNum;i++){
            for (int j=0;j<nodeNum;j++){
                if(ObjectUtils.isEmpty(edgeMapper.selectEdgeByNode12(i,j)))
                    theVertex[i][j]=0;
                else theVertex[i][j]=1;
            }
        }
        return theVertex;

    }





















}
