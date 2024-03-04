<template>
<div class="background">


<!--按钮组-->
  <transition name="fade">
  <v-btn fab style="background-color:#fffaf4;position: absolute;right: 12%;bottom: 11%" v-show="ifShowStart" @click="autoDoIt">
    <img src="../assets/start.png" title="开始" style="width: 20%">
  </v-btn>
  </transition>
  <transition name="fade">
  <v-btn fab style="background-color:#fffaf4;position: absolute;right: 12%;bottom: 11%" v-show="ifShowSuspend" @click="pause">
    <img src="../assets/stop.png" title="暂停" style="width: 20%">
  </v-btn>
  </transition>
  <transition name="fade">
    <v-btn fab style="background-color:#fffaf4;position: absolute;right: 3%;bottom: 11%" v-show="ifShowStart" @click="myDijkstra" :disabled="isNextTipDisabled">
      <img src="../assets/then.png" title="下一步" style="width: 20%">
    </v-btn>
  </transition>
  <transition name="fade">
    <v-btn fab style="background-color:#fffaf4;position: absolute;right: 7.5%;bottom: 2%" v-show="ifShowReplay" @click="replay">
      <img src="../assets/replay.png" title="重新进行" style="width: 20%">
    </v-btn>
  </transition>

  <div>
    <span style="background-color:#0cabf3;width: 20px;height: 20px;position:absolute;left: 7%;bottom: 0.3%; "></span>
    <span style="position:absolute;left: 9%;bottom: 0%;font-family:华文新魏">起始点</span>
    <span style="background-color:#ffb15c;width: 20px;height: 20px;position:absolute;left: 14%;bottom: 0.3%; "></span>
    <span style="position:absolute;left: 16%;bottom: 0%;font-family:华文新魏">即将处理的点</span>
    <span style="background-color:#91dbff;width: 20px;height: 20px;position:absolute;left: 24%;bottom: 0.3%; "></span>
    <span style="position:absolute;left: 26%;bottom: 0%;font-family:华文新魏">正在处理的点</span>
    <span style="background-color:#82f1d7;width: 20px;height: 20px;position:absolute;left: 34%;bottom: 0.3%; "></span>
    <span style="position:absolute;left: 36%;bottom: 0%;font-family:华文新魏">已处理的点</span>
  </div>

<!--显示各个点-->
  <div style="position: absolute;left: 0%;top:0%;width: 80%;height: 100%;">
<!--    返回-->
    <v-btn fab style="background-color:#fffaf4;position: absolute;left: 1%;top: 1%" @click="goToHome">
      <img src="../assets/lastpace.png" title="返回" style="width: 20%">
    </v-btn>


    <transition-group name="fade">

      <v-btn :id="node.nodeid" fab v-for="node in nodes" :key="node.nodeid" @click="chooseOneNode(node.nodeid)"
             :style="{position: 'absolute',left: node.x,top:node.y}"
             title="选择"
      >
        {{node.nodeid}}
      </v-btn>

      <v-btn  fab v-for="node in nodes" :key="node.nodeid" v-show="ifShowButton(node.nodeid)"
             :style="{position: 'absolute',left: node.x,top:node.y,backgroundColor:'#0cabf3'}"
      >
        {{node.nodeid}}
      </v-btn>
    </transition-group>

  </div>

  <!--    展示提醒-->
  <div style="position: absolute;bottom: 4%;right: 0%">
    <v-snackbar :timeout="1000" :value="true" absolute centered right outlined color="red accent-2" v-model="isShowRed">
      {{content}}
    </v-snackbar>
    <v-snackbar :timeout="1000" :value="true" absolute centered right color="orange darken-2" v-model="isShowYellow">
      {{content}}
    </v-snackbar>
    <v-snackbar :timeout="1000" :value="true" absolute centered right outlined color="success" v-model="isShowGreen">
      {{content}}
    </v-snackbar>
<!--    确定起始点-->
    <transition name="fade">
    <v-chip color="white" label v-show="isNotChoosedNode" style="height: 50px;width: 300px;font-size: 20px;font-family: 楷体;">
      确定点&nbsp;{{choosedNode}}&nbsp;为起始点？
      <v-btn fab @click="confirmChose" style="width: 40px;height: 40px;position:absolute;right: 5%;">
        <img src="../assets/confirm.png" title="暂停" style="width: 20%">
      </v-btn>
    </v-chip>
    </transition>
  </div>

  <!--显示表格-->

<div style="font-family: Monotype Corsiva;position: absolute;right: 0%;top:0%;">

  <v-simple-table v-show="ifShowTable" height="600px">
      <thead>
      <tr>
        <th style="font-size: 25px">Node</th>
        <th style="font-size: 25px">Distance</th>
        <th style="font-size: 25px">PreNode</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="dijk in dijkstra" :key="dijk.index">
        <td :style="{fontSize: '20px'}" :id="dijk.node+'n'">{{dijk.node}}</td>
        <td :style="{fontSize: '20px'}" :id="dijk.node+'d'">{{dijk.distance}}</td>
        <td :style="{fontSize: '20px'}" :id="dijk.node+'p'">{{dijk.prenode}}</td>
      </tr>
      </tbody>
  </v-simple-table>

  <v-progress-circular
      :size="50"
      color="primary"
      indeterminate
      v-show="ifCircle"
  ></v-progress-circular>

  <v-btn small fab style="position:absolute;bottom: 1%;right: 1%;" @click="getResultTable">
    <img src="../assets/ask.png" title="显示结果表格" style="width: 15%">
  </v-btn>




</div>


<!--  显示结果-->

    <div
        style="background-color:white;font-family: Monotype Corsiva;position:absolute;right: 0%;top: 0%;"
        v-show="ifShowResult"  id="resultTable"
    >
      <v-simple-table height="600px">
        <thead>
        <tr>
          <th style="font-size: 25px">Node</th>
          <th style="font-size: 25px">Distance</th>
          <th style="font-size: 25px">Route</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="dijk in resultDijk" :key="dijk.index">
          <td :style="{fontSize: '20px'}">{{dijk.node}}</td>
          <td :style="{fontSize: '20px'}">{{dijk.distance}}</td>
          <td :style="{fontSize: '20px'}">{{dijk.prenode}}</td>
        </tr>
        </tbody>
      </v-simple-table>
      <v-btn small fab style="position:absolute;bottom: 1%;right: 1%;" @click="clearResult">
        <img src="../assets/cancle.png" title="返回原表格" style="width: 15%">
      </v-btn>

      <div style="font-family: 华文新魏;text-align: center;font-size: 20px">起始点：{{choosedNode}}</div>
    </div>

</div>
</template>

<script>
import request from "@/utils/request";
import LeaderLine from "leader-line";

export default {
  name: "Dijkstra",

  data(){
    return{
      edges:[],nodes:[],line:[],
      ifShowStart:false,ifShowSuspend:false,ifCircle:false,ifShowTable:false,ifShowReplay:false,ifShowResult:false,
      isShowYellow:false,isShowGreen:false,isShowRed:false,content:'',isNextTipDisabled:false,
      isNotChoosedNode:true,
      choosedNode:'',

      isConfirmBegNodeFlag:false,

      dijkstra:[],

      processStep:0,
      minDij:'',connectLine:[],

      timeInterval:'',
      resultDijk:[],

    }
  },

  methods:{

    hideAllEdges(){
      for (var i=0;i<this.line.length;i++)this.line[i].hide();
    },

    goToHome(){
      this.hideAllEdges();
      this.$router.push('/')
    },

    getNodes(){
      request.post("/api/node/getNode",{

      }) .then( (response) => {
        console.log(response);
        this.nodes=response;
      })
          .catch((error) => {
            console.log(error);
          });
    },

    getAllEdges(){
      request.post("/api/edge/getEdges",{

      }) .then( (response) => {
        console.log(response);
        this.edges=response;

        for (var i=0;i<response.length;i++){
          this.line[i] = new LeaderLine(
              document.getElementById(response[i].begnode),
              document.getElementById(response[i].endnode),
              {
                startPlugColor: '#1abce0',
                endPlugColor: '#05f8f8',
                gradient: true,
                dash: {animation: true},

                middleLabel: LeaderLine.pathLabel({text: response[i].weight.toString(),fontSize:30}),
                color: '#fdfdfd',
                dropShadow: {dx: 0, dy: 3},
                duration: 500,
                timing: [0.58, 0, 0.42, 1],
                hide:true,
                path:'magnet',
              }
          );
          this.line[i].show('draw',{
            animOptions:{
              duration: 500, timing: [0.58, 0, 0.42, 1]
            }
          })
        }

      })
          .catch((error) => {
            console.log(error);
          });

    },

    //确认起始点：
    chooseOneNode(nodeId){
      if (!this.isConfirmBegNodeFlag)this.choosedNode=nodeId;
    },

    confirmChose(){
      if (this.choosedNode===''){
        this.content='请确定起始点';
        this.isShowRed=true;
      }else{
        request.post("/api/dijkstra/setDijkStra",{
          node:parseInt(this.choosedNode),
          distance:'0',
          prenode:this.choosedNode,
          selected:'0'
        }) .then( (response) => {
          this.isNotChoosedNode=false;
          this.ifShowStart=true;
          this.ifShowReplay=true;
          this.isConfirmBegNodeFlag=true;
          console.log(response);
          this.getDijkstraTable();

          document.getElementById(this.choosedNode+'d').className="fadenum";
          document.getElementById(this.choosedNode+'p').className="fadenum";

          setTimeout(()=>{
            document.getElementById(this.choosedNode+'d').className="";
            document.getElementById(this.choosedNode+'p').className="";
          },1000)

        })
            .catch((error) => {
              console.log(error);
            });
      }

    },

    //改变选的点
    ifShowButton(nodeid){
      if (this.choosedNode===nodeid)return true;
      else return false;
    },

    //得到表格
    getDijkstraTable(){
      request.post("/api/dijkstra/getDijkstraTable",{

      }) .then( (response) => {
        console.log(response);
        this.dijkstra=response;
        for (var i=0;i<response.length;i++)
          document.getElementById(response[i].node+'d').innerText=response[i].distance;


      })
          .catch((error) => {
            console.log(error);
          });
    },


    //运行的步骤：
    myDijkstra(){
      if (this.processStep===0)this.getMinDij();
      else if (this.processStep===1)this.findConnectNode();
      else if (this.processStep===2)this.compareAndReplace();
      else if (this.processStep===-1){
        this.content='所有顶点已被标记，dijkstra算法结束！'
        this.isShowGreen=true;
        this.ifShowSuspend=false;
        this.ifShowStart=false;
        setTimeout(()=>{
          this.getResult();
        },1000);

      }
    },

    //自动运行
    autoDoIt(){
      this.ifShowSuspend=true;
      this.isNextTipDisabled=true;
      if (this.processStep===0){
        this.getMinDij();

        setTimeout(()=>{
          this.findConnectNode();
        },1500);

        setTimeout(()=>{
          this.compareAndReplace();
        },3000);
      }

      else if (this.processStep===1){
        setTimeout(()=>{
          this.findConnectNode();
        },1500);

        setTimeout(()=>{
          this.compareAndReplace();
        },3000);
      }

      else if (this.processStep===2){
        setTimeout(()=>{
          this.compareAndReplace();
        },3000);
      }

      if (this.processStep===-1){
        this.content='所有顶点已被标记，dijkstra算法结束！';
        this.isShowGreen=true;
        clearInterval(this.timeInterval);
        this.ifShowSuspend=false;
        this.ifShowStart=false;
        setTimeout(()=>{
          this.getResult();
        },1000);
      }

      this.timeInterval = setInterval(()=>{
        if (this.processStep!==-1){
          this.getMinDij();

          setTimeout(()=>{
            this.findConnectNode();
          },1500);

          setTimeout(()=>{
            this.compareAndReplace();
          },3000);
        }

        if (this.processStep===-1){
          this.content='所有顶点已被标记，dijkstra算法结束！';
          this.isShowGreen=true;
          clearInterval(this.timeInterval);
          this.ifShowSuspend=false;
          this.ifShowStart=false;
          setTimeout(()=>{
            this.getResult();
          },1000);
        }

      },4500);

    },

    pause(){
      this.isNextTipDisabled=false;
      clearInterval(this.timeInterval);
      this.ifShowSuspend=false;
    },



    // 0.得到未标记的最小的点a，标记上
    getMinDij(){
      request.post("/api/dijkstra/getMinDij",{

      }) .then( (response) => {
        console.log(response);
        this.minDij=response;
        document.getElementById(response.node+'n').style.backgroundColor="#91dbff";
        document.getElementById(response.node).style.backgroundColor="#91dbff";
        document.getElementById(response.node+'n').className='fadenum';

        this.content='找到未标记且距离最小的点：'+response.node;
        this.isShowGreen=true;

        setTimeout(()=>{
          document.getElementById(response.node+'n').className='';
        },1000);

        this.processStep++;
      })
          .catch((error) => {
            console.log(error);
          });
    },

    // 1.找到点a 相邻 的 未标记 的点b
    //传入：边，对象的入点;返回：边，出点是未标记且邻接的点，边的id是line[] 的index
    findConnectNode(){
      request.post("/api/dijkstra/findConnectNode",{
        begnode:this.minDij.node,
      }) .then( (response) => {
        console.log(response);
        this.connectLine=response;
        this.processStep++;
        var myContent = '';
        for (var i=0;i<response.length;i++){
          myContent+=(response[i].endnode+' ');
          document.getElementById(response[i].endnode).style.backgroundColor="#ffb15c";
          this.line[response[i].edgesId].setOptions({
            color:'#ffb15c',
            startPlugColor: '#ffdcb1',
            endPlugColor: '#ffb15c',
          });
          document.getElementById(response[i].endnode+'d').style.backgroundColor='#ffb15c';
          document.getElementById(response[i].endnode+'d').className='fadenum';

          setTimeout(()=>{
            document.getElementById(response[i].endnode+'d').className='';
          },1000);

        }
        if (myContent===''){
          this.content='没有相邻且未被标记的点！';
          this.isShowYellow=true;
        }else{
          this.content='找到相邻且未被标记的点：'+myContent;
          this.isShowGreen=true;
        }

      })
          .catch((error) => {
            console.log(error);
          });


    },

    //2.比较起始点o->b与o->a->b的大小，如果o->a->b小，改变distance为o->a->b，并且把preNode换成a
    //0->b:表格；0->a：表格；a->b:connectLine

    compareAndReplace(){

      for (var i=0;i<this.connectLine.length;i++){

        var tline = this.connectLine[i];
        var whichDij=parseInt(tline.endnode);

        for (var i1=0;i1<this.dijkstra.length;i1++){
          if (this.dijkstra[i1].node===tline.endnode)whichDij=i1;//whichDij为tlind对应的点在表格的第几行（从0开始）,是dijkstra[]数组的下标
          //区分：dijkstra数组的下标和此下标对应的node值不一定一样
        }


        var ob = this.dijkstra[whichDij].distance;
        if(ob==='∞')ob = 99999;

        var oa = this.minDij.distance;
        if(oa==='∞')oa = 99999;
        var ab = parseInt(tline.weight);
        var dis = parseInt(ab)+parseInt(oa);


        if (dis<ob){//需要替换
          document.getElementById(tline.endnode+'d').innerText=oa+'+'+ab+'<'+(ob===99999?'∞':ob);
          document.getElementById(tline.endnode+'d').style.color='#ff660c';
          document.getElementById(tline.endnode+'p').style.color='#ff660c';
          request.post("/api/dijkstra/changeDisAndPre",{
            node:tline.endnode,
            distance:dis,
            prenode:this.minDij.node,
            selected:this.dijkstra[whichDij].selected
          }) .then( (response) => {
            console.log(response);

          })
              .catch((error) => {
                console.log(error);
              });

        }else{
          document.getElementById(tline.endnode+'d').innerText=(oa==='99999'?'∞':oa)+'+'+ab+'≥'+(ob===99999?'∞':ob);
        }

        //恢复
          document.getElementById(tline.endnode+'d').style.backgroundColor='#FFFFFF';
          document.getElementById(tline.endnode+'d').className='fadenum';
          document.getElementById(tline.endnode).style.backgroundColor='#FFFFFF';
          this.line[tline.edgesId].setOptions({
            color:'#ffffff',
            startPlugColor: '#1abce0',
            endPlugColor: '#05f8f8',
          });

        setTimeout(()=>{
          document.getElementById(tline.endnode+'d').className='';
        },1000);


      }

      //更新表格信息
      setTimeout(()=>{
        this.getDijkstraTable();
      },1000);

      //标记已经使用过的点(最小的那个)
      document.getElementById(this.minDij.node+'n').style.backgroundColor="#82f1d7";
      document.getElementById(this.minDij.node).style.backgroundColor="#82f1d7";
      document.getElementById(this.minDij.node+'n').className='fadenum';

      //判断所有点是否全被标记
      request.post("/api/dijkstra/ifAllMap",{

      }) .then( (response) => {
        console.log(response);
        if (response.code==='0')this.processStep=-1;
        else this.processStep=0;
      })
          .catch((error) => {
            console.log(error);
          });
    },


    //产生结果
    //传入：起始点
    getResult(){
      request.post("/api/dijkstra/getResult",{
        node:this.choosedNode
      }) .then( (response) => {
        console.log(response);
        this.resultDijk = response;
        this.ifShowResult=true;
        document.getElementById("resultTable").className="fadenumR";
        setTimeout(()=>{
          document.getElementById("resultTable").className="";
        },1000);
      })
          .catch((error) => {
            console.log(error);
          });
    },

    clearResult(){
      this.ifShowResult=false;
      document.getElementById("resultTable").className="fadenumR";
      setTimeout(()=>{
        document.getElementById("resultTable").className="";
      },1000);
    },
    getResultTable(){
      if (this.processStep===-1){
        this.ifShowResult=true;
        document.getElementById("resultTable").className="fadenumR";
        setTimeout(()=>{
          document.getElementById("resultTable").className="";
        },1000);
      }else{
        this.content="算法未结束，请在结束算法后点击";
        this.isShowYellow=true;
      }
    },


    //重新建表
    createTable(){
      request.post("/api/dijkstra/createTable",{

      }) .then( (response) => {
        console.log(response);
      })
          .catch((error) => {
            console.log(error);
          });

    },
    replay(){
      this.createTable();
      location.reload();
    },

  },

  mounted() {
    this.ifCircle=true;
    this.getNodes();
    this.$nextTick(()=>{
      setTimeout(()=>{
        this.getAllEdges();
        this.getDijkstraTable();
        this.ifShowTable=true;
        this.ifCircle=false;

      },1000);




    })
  },

}
</script>

<style scoped>
.background {
  background: url("../assets/background.png");
  background-size: 100% 100%;
  height: 100%;
  position: fixed;
  width: 100%
}

.fade-enter{
  opacity: 0;
}
.fade-enter-active{
  transition: opacity 1s;
}

.fade-leave-to{
  opacity: 0;
}
.fade-leave-active{
  transition: opacity 1s;
}

.fadenum{
  animation:fadenum 1s 1;
}

@keyframes fadenum{
  0%{opacity: 0;}
  100%{opacity: 1;}
}

.fadenumR{
  animation:fadenumR 1s 1;
  height: 600px;
}

@keyframes fadenumR{
  0%{opacity: 0;}
  100%{opacity: 1;}
}

</style>
