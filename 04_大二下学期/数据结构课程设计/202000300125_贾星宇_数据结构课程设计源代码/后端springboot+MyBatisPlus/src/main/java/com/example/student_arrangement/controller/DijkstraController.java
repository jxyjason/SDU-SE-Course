package com.example.student_arrangement.controller;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.example.student_arrangement.common.Result;
import com.example.student_arrangement.entity.Dijkstra;
import com.example.student_arrangement.entity.Edges;
import com.example.student_arrangement.entity.Nodes;
import com.example.student_arrangement.mapper.DijkstraMapper;
import com.example.student_arrangement.mapper.EdgeMapper;
import com.example.student_arrangement.mapper.NodeMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.xml.soap.Node;

@RestController
@CrossOrigin

@RequestMapping(value = "/dijkstra")
public class DijkstraController {
    @Resource
    EdgeMapper edgeMapper;
    @Resource
    NodeMapper nodeMapper;
    @Resource
    DijkstraMapper dijkstraMapper;

    @RequestMapping(value = "/createTable",method = RequestMethod.POST)
    public Result<?> createTable(@RequestBody Dijkstra dijkstra){
        int nodeNum = nodeMapper.getNodeNums();
        if (dijkstraMapper.selectAll().length!=0) dijkstraMapper.deleteAll();
        Nodes[] nodes = nodeMapper.selectAll();
        for (int i=0;i<nodeNum;i++){
            int nodeName = nodes[i].getNodeid();
            dijkstraMapper.insertDij(nodeName,"∞","","0");
        }

        return Result.success();
    }

    @RequestMapping(value = "/getDijkstraTable",method = RequestMethod.POST)
    public Dijkstra[] getDijkstraTable(@RequestBody Dijkstra dijkstra){
        return dijkstraMapper.selectAll();
    }

    @RequestMapping(value = "/setDijkStra",method = RequestMethod.POST)
    public Result<?> setDijkStra(@RequestBody Dijkstra dijkstra){
        dijkstraMapper.updateById(dijkstra.getDistance(),dijkstra.getPrenode(),dijkstra.getSelected(),dijkstra.getNode());
        return Result.success();
    }

    //得到最小的
    @RequestMapping(value = "/getMinDij",method = RequestMethod.POST)
    public Dijkstra getMinDij(@RequestBody Dijkstra dijkstra){
        Dijkstra dijkstra1[] = dijkstraMapper.selectAll();
        int min = 0;
        while (dijkstra1[min].getSelected().equals("1"))min++;
        for (int i=0;i<dijkstra1.length;i++) {
            if (dijkstra1[i].getSelected().equals("1")){
                dijkstra1[i].setDistance("99999");
                continue;
            }
            if (dijkstra1[i].getDistance().equals("∞"))dijkstra1[i].setDistance("99999");
            if (Integer.parseInt(dijkstra1[min].getDistance()) > Integer.parseInt(dijkstra1[i].getDistance()))
                min = i;
        }
        dijkstraMapper.
                updateById(dijkstra1[min].getDistance().equals("99999")?"∞":dijkstra1[min].getDistance(),
                        dijkstra1[min].getPrenode(),"1",dijkstra1[min].getNode());
        return dijkstra1[min];

    }


    //找到相邻的且未标记的点
    //EdgeMapper找相邻，找到后去DijkstraMapper判断
    //返回对象为边
    @RequestMapping(value = "/findConnectNode",method = RequestMethod.POST)
    public Edges[] findConnectNode(@RequestBody Edges edges){
        Edges allEdges[] = edgeMapper.getAllEdges();
        Edges edges1[] = edgeMapper.selectEdgeByBegNode(edges.getBegnode());//找到连接的所有点
        Edges edges2[] = new Edges[edges1.length];//备用
        int ind = 0;
        for (int i=0;i<edges1.length;i++)//找1中未选中的点（selected==0），同时得到有多少点（ind）
            if (dijkstraMapper.selectByNode(edges1[i].getEndnode()).getSelected().equals("0"))edges2[ind++]=edges1[i];

        Edges edges3[] = new Edges[ind];//结果边
        for (int i=0;i<ind;i++) {
            edges3[i] = edges2[i];
            for (int j=0;j<allEdges.length;j++){//将边的id设置成它是所有边中的第几个，方便操作leaderLine
                if (edges3[i].getEdgesId()==allEdges[j].getEdgesId())edges3[i].setEdgesId(j);
            }
        }
        return edges3;


    }

    @RequestMapping(value = "/changeDisAndPre",method = RequestMethod.POST)
    public Result<?> changeDisAndPre(@RequestBody Dijkstra dijkstra){
        dijkstraMapper.
                updateById(dijkstra.getDistance(),dijkstra.getPrenode(),dijkstra.getSelected(),dijkstra.getNode());
        return Result.success();
    }

    @RequestMapping(value = "/ifAllMap",method = RequestMethod.POST)
    public Result<?> ifAllMap(@RequestBody Dijkstra dijkstra){
        Dijkstra[] dijkstras = dijkstraMapper.selectBySelected("0");
        if (dijkstras.length==0)return Result.srror("0","all selected");
        else return Result.srror("1","not all selected");

    }

    @RequestMapping(value = "/getResult",method = RequestMethod.POST)
    public Dijkstra[] getResult(@RequestBody Dijkstra dijkstra){
        Dijkstra result[] = dijkstraMapper.selectAll();
        Dijkstra theDijk[] = dijkstraMapper.selectAll();
        int begNode = dijkstra.getNode();
        for(int i=0;i<theDijk.length;i++){
            Dijkstra temp = theDijk[i];

            if (temp.getPrenode().equals("")){
                result[i].setPrenode("No Path!");
                continue;
            }

            int[] res = new int[theDijk.length];
            int count = 0;
            while (temp.getNode()!=begNode){
                res[count++] = temp.getNode();
                temp = dijkstraMapper.selectByNode(Integer.parseInt(temp.getPrenode()));
            }
            res[count] = begNode;
            String resPath = "";
            for (int j=count;j>=0;j--){
                if (j!=0)resPath += res[j]+" -> ";
                else resPath += res[j];
            }
            result[i].setPrenode(resPath);
        }

        return result;



    }

}
