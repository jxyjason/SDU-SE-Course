<template>
  <div class="background">
    <div v-show="whenCreate">

<!--    添加点-->
<!--      添加点-->
    <v-btn fab style="position: absolute;right: 15%;top: 3%" @click="addNodes()">
      <img src="../assets/add.png" title="添加顶点" style="width: 20%">
    </v-btn>
    <v-btn fab style="position: absolute;right: 15%;top: 11%" @click="whenClickDelete">
      <img src="../assets/delete.png" title="删除" style="width: 20%">
    </v-btn>
      <transition name="fade">
    <v-btn fab style="background-color:#fffaf4;position: absolute;right: 15%;top: 11%" v-show="ifShowDeleteBack" @click="whenClickBack">
      <img src="../assets/back.png" title="返回" style="width: 20%">
    </v-btn>
      </transition>
      <transition name="fade">
    <v-btn fab small style="background-color:#fffaf4;position: absolute;right: 15.45%;top: 19%" v-show="ifShowDeleteAll"
           @click="confirmDelete">
      <img src="../assets/deleteAll.png" title="全部删除" style="width: 10%">
    </v-btn>
      </transition>

    <div style="position:absolute;right:1%;top: 3%;font-family: 楷体;display: flex;background-color:#fffaf4;border-radius: 20%;padding: 10px">
      <div>
    <v-text-field
        label="编号"
        v-model="nodeId"
    ></v-text-field>
    <v-text-field
        label="横坐标"
        v-model="x"
    >

    </v-text-field>
    <v-text-field
        label="纵坐标"
        v-model="y"
    >
    </v-text-field>
      </div>
    </div>

<!--      确认-->

      <v-dialog v-model="confirmDeleteDialog" max-width="400px">
        <div style="background-color:white;font-family: 楷体;font-size: 30px;text-align: center">

          <img src="../assets/ask.png" alt="ask" style="width: 20%">
          全部删除？
          <br>
          <v-btn fab  @click="deleteAll" style="margin-right: 30px">
            <img src="../assets/confirm.png" alt="confirm" style="width: 50px">
          </v-btn>
          <v-btn fab @click="disConfirmDelete" >
            <img src="../assets/cancle.png" alt="cancle" style="width: 50px">
          </v-btn>
        </div>
      </v-dialog>

      <!--    显示各个点-->
      <div style="position: absolute;left: 8%;top:0%;width: 70%;height: 100%;">

        <transition-group name="fade">
          <v-btn :id="node.nodeid" fab v-for="node in nodes" :key="node.nodeid"
                 :style="{position: 'absolute',left: node.x,top:node.y}"
          >
            {{node.nodeid}}<br>({{node.x.split('%')[0]}},{{node.y.split('%')[0]}})
          </v-btn>
        </transition-group>

        <transition-group name="fade">
          <v-btn fab v-for="node in nodes" :key="node.nodeid" v-show="ifDeleteCertain" @click="deleteCertain(node.nodeid)"
                 :style="{position: 'absolute',left: node.x,top:node.y}" >
            <img src="../assets/delete.png" title="删除" style="width: 20%" v-show="ifDeleteCertain">
          </v-btn>
        </transition-group>

        <img src="../assets/xLine.png" alt="xline" style="position:absolute;left: -0.8%;top: -1.5%;width: 100%;z-index: -1;">
        <img src="../assets/yLine.png" alt="yLine" style="position:absolute;left: -2%;top: 2%;height: 100%;z-index: -1;">
      </div>

<!--      添加边-->
<!--      添加边-->
      <v-btn fab style="position: absolute;right: 15%;top: 35%" @click="addEdge()">
        <img src="../assets/add.png" title="添加边" style="width: 20%">
      </v-btn>
      <v-btn fab style="background-color:#fffaf4;position: absolute;right: 15%;top: 43%" @click="lastPace">
        <img src="../assets/lastpace.png" title="撤回" style="width: 20%">
      </v-btn>
      <div style="position:absolute;right:1%;top: 35%;font-family: 楷体;display: flex;background-color:#fffaf4;border-radius: 20%;padding: 10px">
        <div>
          <v-text-field
              label="起点"
              v-model="begNode"
          ></v-text-field>
          <v-text-field
              label="终点"
              v-model="endNode"
          >

          </v-text-field>
          <v-text-field
              label="权值"
              v-model="weight"
              @click="clickWeight"
          >
          </v-text-field>
        </div>
      </div>


<!--      随机生成-->
      <v-btn fab style="position: absolute;right: 15%;top: 67%" @click="autoCreate">
        <img src="../assets/add.png" title="自动生成" style="width: 20%">
      </v-btn>
      <v-btn fab style="position: absolute;right: 15%;top: 75%" @click="showDataStruct">
        <img src="../assets/go.png" title="开始进行dijkstra算法" style="width: 20%">
      </v-btn>
      <div style="position:absolute;right:1%;top: 67%;font-family: 楷体;display: flex;background-color:#fffaf4;border-radius: 20%;padding: 10px">
        <div>
          <v-text-field
              label="点的个数"
              v-model="nodeNum"
              @click="clearNodeNum"
          ></v-text-field>
          <v-text-field
              label="边的个数"
              v-model="edgeNum"
              @click="clearEdgeNum"
          >

          </v-text-field>

        </div>

        <v-progress-circular
            :size="50"
            color="primary"
            indeterminate
            v-show="ifCircle"
        ></v-progress-circular>
      </div>


<!--      邻接链表/矩阵-->
      <v-dialog
      v-model="ifShowLinJieDialog"
      max-width="950"
      transition="dialog-bottom-transition"
      >
        <div style="font-family: 华文新魏;font-size: 30px;background-color:white;">请选择数据结构</div>
        <div style="background-color:white;"><br></div>

        <div style="background-color:white;text-align: center;">
          <v-row>
            <v-col>

              <div style="font-family: 华文新魏;font-size:20px;">邻接矩阵</div>
              <v-btn height="95%" @click="goToDij">
          <v-simple-table height="400px" style="font-family: Monotype Corsiva;">
            <thead>
            <tr>
              <th  style="background-color:#0ba5f3;"></th>
              <th v-for="i in vertexValue.length" :key="i" style="background-color:#91dbff;">{{i-1}}</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(vertexRow,index) in vertexValue" :key="vertexRow">
              <td  style="background-color:#91dbff;">{{ index }}</td>
              <td v-for="vertexCol in vertexRow" :key="vertexCol" >{{vertexCol}}</td>
            </tr>
            </tbody>
          </v-simple-table>

          </v-btn>
            </v-col>

            <v-col>
              <div style="font-family: 华文新魏;font-size:20px;">邻接链表</div>
          <v-btn height="95%" @click="goToDij">
            <div style="font-family: Monotype Corsiva;height: 400px;text-align: left">

              <div v-for="(linked,index) in linkedValue" :key="linked">
                <v-chip label style="background-color:#91dbff; padding-right: 0;font-size: 25px;margin-bottom: 10px;">{{ index }}--</v-chip>
                <v-chip label v-for="linkedEle in linked" :key="linkedEle"
                        style="padding-left: 0;padding-right: 0;font-size: 25px;background-color:white;margin-bottom: 10px;"
                >
                  <span v-show="linkedEle!==''" style="">-->{{linkedEle}}--</span>
                  <span v-show="linkedEle===''" style="">-->null</span>
                </v-chip>
                <br>
              </div>

            </div>
          </v-btn>
            </v-col>
          </v-row>
        </div>
      </v-dialog>


<!--      展示提示信息-->
      <v-dialog
          v-model="ifShowIntroDoc"
          max-width="800"
          transition="dialog-bottom-transition"
      >
        <div style="background-color:#c1f6ff;font-family: 华文新魏;font-size: 30px;">
          <h3 style="margin: 0 auto;text-align: center"><img src="../assets/ask.png" style="width: 30px">侧边栏使用说明</h3>
          <p>第一部分为顶点操作表单，左侧按钮从上到下分别为<img src="../assets/add.png" style="width: 30px">添加顶点、
            <img src="../assets/delete.png" style="width: 30px">删除顶点；
            点击删除按钮后，再次点击<img src="../assets/back.png" style="width: 30px">可返回删除状态，点击红色删除按钮
            <img src="../assets/deleteAll.png" style="width: 30px">可删除整个图，点击各个顶点上的蓝色按钮
            <img src="../assets/delete.png" style="width: 30px">可删除相应顶点及其关联的边。</p>
          <div  style="margin: 0 auto;text-align: center">------------------------------</div>
          <p>第二部分为边操作表单，左侧按钮从上到下分别为<img src="../assets/add.png" style="width: 30px">添加边、
            <img src="../assets/lastpace.png" style="width: 30px">撤回；点击撤回按钮可以撤回上一步添加的边。</p>
          <div  style="margin: 0 auto;text-align: center">------------------------------</div>
          <p>第三部分为边操作表单，左侧按钮从上到下分别为<img src="../assets/add.png" style="width: 30px">自动创建有向图、
            <img src="../assets/go.png" style="width: 30px">进入dijkstra算法界面。</p>

        </div>
      </v-dialog>
      <v-btn style="font-family: 华文新魏;font-size: 15px;position: absolute;right: 0%;bottom: 0%;" @click="showInfoDocDialog">
        操作说明
      </v-btn>








<div style="position:absolute;right: 0%;bottom: 4%;">
    <v-snackbar :timeout="1000" :value="true" absolute centered right outlined color="red accent-2" v-model="isNotAllFill">
      请将信息填写正确且完整
    </v-snackbar>
    <v-snackbar :timeout="1000" :value="true" absolute centered right outlined color="red accent-2" v-model="isRepeat">
      此值已存在，不可重复加入！
    </v-snackbar>
    <v-snackbar :timeout="1000" :value="true" absolute centered right outlined color="success" v-model="isSucc">
      添加成功！
    </v-snackbar>
    <v-snackbar :timeout="1000" :value="true" absolute centered right outlined color="success" v-model="isDelete">
      删除成功！
    </v-snackbar>
  <v-snackbar :timeout="1000" :value="true" absolute centered right outlined color="red accent-2" v-model="isFirstPace">
    已经是第一步了！
  </v-snackbar>
  <v-snackbar :timeout="1000" :value="true" absolute centered right outlined color="red accent-2" v-model="isManyEdges">
    边的数目过多！
  </v-snackbar>

</div>









    </div>
  </div>
</template>

<script>
import request from "../utils/request.js";
import LeaderLine from 'leader-line';
import Dijkstra from "@/views/Dijkstra";

  export default {
    name: 'Home',
    components: Dijkstra,
    data(){
      return{
        whenCreate:true,

        // 点
        nodeId:'',
        x:'',
        y:'',
        nodes:[],
        ifDeleteCertain:false,ifShowDeleteAll:false,ifShowDeleteBack:false,isFirstPace:false,isManyEdges:false,ifShowIntroDoc:false,
        isNotAllFill:false,isRepeat:false,isSucc:false,isDelete:false,confirmDeleteDialog:false,ifCircle:false,ifShowLinJieDialog:false,
        // 边
        begNode:'',
        endNode:'',
        weight:'1',
        edges:[],line:[],//line是画出来的leaderLine的实例

        // 自动添加
        nodeNum:0,
        edgeNum:0,

        vertexValue:'',linkedValue:[],




      }
    },

    methods:{


      // 点的操作
      addNodes(){
        if (this.nodeId===''||this.x===''||this.y===''){
          this.isNotAllFill=true
        }else {
          request.post("/api/node/saveNode",{
            nodeid:this.nodeId,
            x:this.x+'%',
            y:this.y+'%'
          }) .then( (response) => {
            console.log(response);
            if (response.code==='0'){
              this.isSucc=true;
              this.getNodes();
              this.nodeId='';this.x='';this.y='';
            }
            else if (response.code==='1')this.isRepeat=true;
          })
              .catch((error) => {
                console.log(error);
              });
        }
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

      whenClickDelete(){
        this.ifDeleteCertain=true;
        this.ifShowDeleteAll=true;
        this.ifShowDeleteBack=true;
      },
      whenClickBack(){
        this.ifDeleteCertain=false;
        this.ifShowDeleteAll=false;
        this.ifShowDeleteBack=false;
      },

      confirmDelete(){this.confirmDeleteDialog=true;},
      deleteAll(){
        request.post("/api/node/deleteAll",{

        }) .then( (response) => {
          console.log(response);
          if (response.code==='0'){
            this.isDelete=true;
            this.confirmDeleteDialog=false;
            this.whenClickBack();
            location.reload();
          }
        })
            .catch((error) => {
              console.log(error);
            });
      },
      disConfirmDelete(){this.confirmDeleteDialog=false;},

      async deleteCertain(id){
        request.post("/api/node/deleteCertain",{
          nodeid:id,
        }) .then( (response) => {
          console.log(response);
          if (response.code==='0'){
            this.hideAllEdges();
            this.getNodes();
            this.getAllEdges();
            this.isDelete=true;
          }
        })
            .catch((error) => {
              console.log(error);
            });



      },

// 边的操作
      clickWeight(){this.weight='';},

      addEdge(){
        if (this.begNode===''||this.endNode===''||this.begNode===this.endNode)this.isNotAllFill=true;
        else{
          request.post("/api/edge/saveEdge",{
            begnode:this.begNode,
            endnode:this.endNode,
            weight:this.weight
          }) .then( (response) => {
            console.log(response);
            if (response.code==='0'){
              this.isSucc=true;
              this.begNode='';this.endNode='';this.weight='1';
              this.drawEdge();
            }
            else if (response.code==='1')this.isRepeat=true;
            else if (response.code==='2')this.isNotAllFill=true;
          })
              .catch((error) => {
                console.log(error);
              });
        }
      },

      drawEdge(){
        request.post("/api/edge/getEdges",{

        }) .then( (response) => {
          console.log(response);
          this.edges=response;

            var ind = response.length-1;
            this.line[ind] = new LeaderLine(
                document.getElementById(response[ind].begnode),
                document.getElementById(response[ind].endnode),
                {
                  startPlugColor: '#1abce0',
                  endPlugColor: '#05f8f8',
                  gradient: true,
                  dash: {animation: true},

                  middleLabel: LeaderLine.pathLabel({text: response[ind].weight.toString(),fontSize:30}),
                  color: '#fdfdfd',
                  dropShadow: {dx: 0, dy: 3},
                  duration: 500,
                  timing: [0.58, 0, 0.42, 1],
                  hide:true,
                  path:'magnet',
                }
            );
            this.line[ind].show('draw',{
              animOptions:{
                duration: 500, timing: [0.58, 0, 0.42, 1]
              }
            })

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

      lastPace(){
        request.post("/api/edge/lastPace",{

        }) .then( (response) => {
          console.log(response);
          if (response.code==='0'){
            this.line[this.line.length-1].hide();
            var ind = parseInt(response.msg);
            delete this.line[ind];
            this.line.length--;
            console.log(this.line)
          }else{
            this.isFirstPace=true;
          }
        })
            .catch((error) => {
              console.log(error);
            });

      },

      hideAllEdges(){
        for (var i=0;i<this.line.length;i++)this.line[i].hide();
      },


      //自动添加
      clearNodeNum(){this.nodeNum=''},
      clearEdgeNum(){this.edgeNum=''},
      showInfoDocDialog(){this.ifShowIntroDoc=true;},

      autoCreate(){
        if(this.nodeNum===''||this.edgeNum===''){
          this.isNotAllFill=true;
        }else if(this.edgeNum>this.nodeNum*(this.nodeNum-1)){
          this.isManyEdges=true;
        }else{
          this.ifCircle=true;
          request.post("/api/edge/autoCreate",{
            begnode:this.nodeNum,
            endnode:this.edgeNum
          }) .then( (response) => {
            console.log(response);
            this.hideAllEdges();
            this.getNodes();

          })
              .catch((error) => {
                console.log(error);
              });

          setTimeout(()=>{
            this.ifCircle=false;
            this.getAllEdges();
          },1500);


        }

      },

      goToDij(){
        this.hideAllEdges();
        this.createTable();
        this.$router.push('/dijkstra');
      },

      createTable(){
        request.post("/api/dijkstra/createTable",{

        }) .then( (response) => {
          console.log(response);
        })
            .catch((error) => {
              console.log(error);
            });

      },


      //生成邻接矩阵
      createVertex(){

            request.post("/api/edge/getIsEdgeByBegAndEnd",{

            }) .then( (response) => {
              console.log(response);
              this.vertexValue=response;

            })
                .catch((error) => {
                  console.log(error);
                });
      },

      createLinked(){
        var theVertex = this.vertexValue;
        this.linkedValue;

        for (var i2=0;i2<theVertex.length;i2++){
          var ind = 0;
          var tempVertex = theVertex[i2];
          var tempLinked = Array();
          for (var j2=0;j2<tempVertex.length;j2++) {
            if (parseInt(tempVertex[j2]) === 1) tempLinked[ind++] = j2;
          }
          tempLinked[ind] = '';
          this.linkedValue[i2] = tempLinked;
        }
        console.log(this.linkedValue);
      },


      showDataStruct(){
        if (this.nodes.length===0){
          this.isNotAllFill=true;
          return;
        }
        this.createVertex();
        this.ifCircle=true;
        setTimeout(()=>{
          this.createLinked();
          this.ifCircle=false;
          this.ifShowLinJieDialog=true;
        },500);

      }

    },

    mounted() {
      this.getNodes();
      this.$nextTick(function(){
        setTimeout(()=>{
          this.getAllEdges();
        },1000);
      })
    }


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


</style>
