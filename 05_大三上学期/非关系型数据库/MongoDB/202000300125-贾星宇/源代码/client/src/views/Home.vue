<template>
  <div>

    <div style="position: absolute;left: 2%;top:2%;height: 98%;width: 28%;background-color:#ffecec;overflow: auto">
      <v-btn @click="selectAllStudents">找出所有学生</v-btn>
      <v-btn @click="selectAllTeacher">找出所有老师</v-btn>
      <v-btn @click="selectAllCourse">找出所有课程</v-btn>
      <v-btn @click="getStudentsByAgeLt20">年龄小于20岁的所有学生</v-btn>
      <v-btn @click="selectStudentsByAgeLe20AndRuanJian">年龄小于20岁且是软件学院的学生</v-btn>
      <v-btn @click="selectAllStudentsNameSex">所有学生姓名和性别</v-btn>

      <v-btn @click="addDataToStudentInJson">向学生表中插入数据</v-btn>
      <v-btn @click="addDataToTeacherInJson">向教师表中插入数据</v-btn>
      <v-btn @click="addDataToCourseInJson">向课程表中插入数据</v-btn>

      <v-btn @click="showChooseCourse">学生选课</v-btn>

      <v-btn @click="getAllCourseNameInStudentCourse()">列出student_course集合中出现过的所有课程名称</v-btn>
      <v-btn @click="getStudent10InAverage()">找出平均成绩排名前10的学生</v-btn>

    </div>

    <div style="position: absolute;left: 30%;top:2%;height: 98%;width: 68%;background-color:#f6f6ff;overflow: auto"
         v-show="ifShowInfo">
      <v-chip v-for="info in theInfo" :key="info.sid" style="overflow: auto;height: 50px;background-color: #9deefa"
      >
        {{ info }}
      </v-chip>

      <div v-for="thecourse in course" :key="thecourse.cid">
        <v-chip style="background-color:#f1d8d8;">{{ thecourse.cid }}--{{ thecourse.name }}
        </v-chip>
      </div>

    </div>


    <div style="position: absolute;left: 30%;top:2%;height: 98%;width: 48%;background-color:#f6f6ff;"
         v-show="ifStudentsJson">
      <v-text-field label="姓名" v-model="NAME"></v-text-field>
      <v-text-field label="年龄" v-model="AGE"></v-text-field>
      <v-text-field label="学院名称" v-model="DNAME"></v-text-field>
      <v-text-field label="性别" v-model="SEX"></v-text-field>
      <v-text-field label="学号" v-model="SID"></v-text-field>
      <v-text-field label="班级" v-model="CLASS"></v-text-field>
      <v-text-field label="生日" v-model="BIRTHDAY"></v-text-field>
      <v-btn @click="addDataToStudentInJsonConfirm">确认</v-btn>

      <!-- 按钮 -->
      <el-upload
          class="upload"
          action=""
          :multiple="false"
          :show-file-list="false"
          accept="csv, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
          :http-request="httpRequest">
        <el-button size="small" type="primary">上传</el-button>
      </el-upload>
      <!-- 按钮 end -->


    </div>

    <div style="position: absolute;left: 30%;top:2%;height: 98%;width: 48%;background-color:#f6f6ff;"
         v-show="ifTeacherJson">
      <v-text-field label="姓名" v-model="NAME"></v-text-field>
      <v-text-field label="年龄" v-model="AGE"></v-text-field>
      <v-text-field label="学院名称" v-model="DNAME"></v-text-field>
      <v-text-field label="性别" v-model="SEX"></v-text-field>
      <v-text-field label="工号" v-model="TID"></v-text-field>
      <v-btn @click="addDataToTeacherInJsonConfirm">确认</v-btn>
      <!-- 按钮 -->
      <el-upload
          class="upload"
          action=""
          :multiple="false"
          :show-file-list="false"
          accept="csv, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
          :http-request="httpRequest">
        <el-button size="small" type="primary">上传</el-button>
      </el-upload>
      <!-- 按钮 end -->
    </div>
    <div style="position: absolute;left: 30%;top:2%;height: 98%;width: 48%;background-color:#f6f6ff;"
         v-show="ifCourseJson">
      <v-text-field label="课程id" v-model="CID"></v-text-field>
      <v-text-field label="学分" v-model="CREDIT"></v-text-field>
      <v-text-field label="先行课id" v-model="FCID"></v-text-field>
      <v-text-field label="课程名" v-model="NAME"></v-text-field>
      <v-btn @click="addDataToCourseInJsonConfirm">确认</v-btn>
      <!-- 按钮 -->
      <el-upload
          class="upload"
          action=""
          :multiple="false"
          :show-file-list="false"
          accept="csv, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
          :http-request="httpRequest">
        <el-button size="small" type="primary">上传</el-button>
      </el-upload>
      <!-- 按钮 end -->
    </div>


    <div style="position: absolute;left: 30%;top:2%;height: 98%;width: 48%;background-color:#f6f6ff;"
         v-show="ifShowChooseCourse">
      <v-text-field label="学号" v-model="SID"></v-text-field>
      <v-btn @click="getCourseBySid">查询已选课程</v-btn>
      <div v-for="thecourse in course" :key="thecourse.cid">
        <v-chip  style="background-color:#f1d8d8;">{{ thecourse.cid }}--{{ thecourse.name }}
        </v-chip>
        <v-chip style="background-color:red;" @click="deleteCourse(thecourse.sid,thecourse.cid)">X</v-chip>
      </div>
      <div>选课：（请点击课程选择）</div>
      <div style="height: 50%;overflow:auto;">
        <div v-for="course in allCourse" :key="course.cid">
          <v-chip
              @click="clickTheCourse(course.cid)"
          >{{ course.cid }}--{{ course.name }},学分：{{ course.credit }}
          </v-chip>
        </div>
      </div>
      <div style="color: red">已选中课：{{ this.choosedCourseId }}</div>
      <v-btn @click="chooseCourse()">选中</v-btn>
    </div>


  </div>
</template>

<script>


import request from "@/utils/request";
import * as XLSX from 'xlsx/xlsx.mjs'

export default {
  name: 'Home',
  components: {},
  data() {
    return {
      theInfo: [],
      AGE: '', DNAME: '', NAME: '', SEX: '', SID: '', CLASS: '', BIRTHDAY: '', TID: '',
      CID: '', CREDIT: '', FCID: '',

      ifStudentsJson: false, ifTeacherJson: false, ifCourseJson: false, ifShowInfo: true, ifShowChooseCourse: false,

      tableData: [], course: [{cid: '', name: ''}], allCourse: [], choosedCourseId: '',

    }
  },

  methods: {

    httpRequest(e) {
      let file = e.file // 文件信息
      console.log('e: ', e)
      console.log('file: ', e.file)

      if (!file) {
        // 没有文件
        return false
      } else if (!/\.(xls|xlsx)$/.test(file.name.toLowerCase())) {
        // 格式根据自己需求定义
        this.$message.error('上传格式不正确，请上传xls或者xlsx格式')
        return false
      }

      const fileReader = new FileReader()
      fileReader.onload = (ev) => {
        try {
          const data = ev.target.result
          const workbook = XLSX.read(data, {
            type: 'binary' // 以字符编码的方式解析
          })
          const exlname = workbook.SheetNames[0] // 取第一张表
          const exl = XLSX.utils.sheet_to_json(workbook.Sheets[exlname]) // 生成json表格内容
          console.log(exl)
          // 将 JSON 数据挂到 data 里
          this.tableData = JSON.stringify(exl)

          console.log(this.tableData)
          request.post('/node/addDataInExcelStudent', {
            whichdata: this.tableData,
          }).then((res) => {
            console.log(res)
          }).catch((e) => {
            console.log(e)
          })
          // document.getElementsByName('file')[0].value = '' // 根据自己需求，可重置上传value为空，允许重复上传同一文件
        } catch (e) {
          console.log(e)
          return false
        }
      }
      fileReader.readAsBinaryString(file)
    },

    ifShowBasicInfo() {
      this.ifShowInfo = true
      this.ifStudentsJson = false
      this.ifTeacherJson = false
      this.ifCourseJson = false
      this.ifShowChooseCourse = false
    },

    getStudentsByAgeLt20() {
      this.ifShowBasicInfo()
      this.theInfo = []
      request.post('/node/selectStudentsByAgeLe20', {}).then(response => {
        this.theInfo = response;
        console.log(response)
      }).catch(err => {
        console.log(err)
      })
    },

    selectStudentsByAgeLe20AndRuanJian() {
      this.ifShowBasicInfo()
      this.theInfo = []
      request.post('/node/selectStudentsByAgeLe20AndRuanJian', {}).then(response => {
        this.theInfo = response;
        console.log(response)
      }).catch(err => {
        console.log(err)
      })
    },

    selectAllCourse() {
      this.ifShowBasicInfo()
      this.theInfo = []
      request.post('/node/selectAllCourse', {}).then(response => {
        this.theInfo = response;
        console.log(response)
      }).catch(err => {
        console.log(err)
      })
    },

    selectAllStudents() {
      this.ifShowBasicInfo()
      this.theInfo = []
      request.post('/node/selectAllStudents', {}).then(response => {
        this.theInfo = response;
        console.log(response)
      }).catch(err => {
        console.log(err)
      })
    },

    selectAllTeacher() {
      this.ifShowBasicInfo()
      this.theInfo = []
      request.post('/node/selectAllTeacher', {}).then(response => {
        this.theInfo = response;
        console.log(response)
      }).catch(err => {
        console.log(err)
      })
    },

    selectAllStudentsNameSex() {
      this.ifShowBasicInfo()
      this.theInfo = []
      request.post('/node/selectAllStudentsNameSex', {}).then(response => {
        this.theInfo = response;
        console.log(response)
      }).catch(err => {
        console.log(err)
      })
    },


    addDataToStudentInJson() {
      this.ifStudentsJson = true
      this.ifTeacherJson = false
      this.ifCourseJson = false
      this.ifShowInfo = false
      this.ifShowChooseCourse = false
    },
    addDataToStudentInJsonConfirm() {
      if (this.ifStudentsJson === true) {
        request.post('/node/addDataToStudentInJson', {
          sid: this.SID,
          name: this.NAME,
          sex: this.SEX,
          age: this.AGE,
          birthday: this.BIRTHDAY,
          dname: this.DNAME,
          class: this.CLASS,

        }).then(response => {
          console.log(response)
        }).catch(err => {
          console.log(err)
        })
      }
    },

    addDataToTeacherInJson() {
      this.ifStudentsJson = false
      this.ifTeacherJson = true
      this.ifCourseJson = false
      this.ifShowInfo = false
      this.ifShowChooseCourse = false
    },
    addDataToTeacherInJsonConfirm() {
      if (this.ifTeacherJson === true) {
        request.post('/node/addDataToTeacherInJsonConfirm', {
          name: this.NAME,
          dname: this.DNAME,
          age: this.AGE,
          sex: this.SEX,
          tid: this.TID,
        }).then(response => {
          console.log(response)
        }).catch(err => {
          console.log(err)
        })
      }
    },

    addDataToCourseInJson() {
      this.ifStudentsJson = false
      this.ifTeacherJson = false
      this.ifCourseJson = true
      this.ifShowInfo = false
      this.ifShowChooseCourse = false
    },
    addDataToCourseInJsonConfirm() {
      if (this.ifCourseJson === true) {
        request.post('/node/addDataToCourseInJsonConfirm', {
          name: this.NAME,
          cid: this.CID,
          credit: this.CREDIT,
          fcid: this.FCID,

        }).then(response => {
          console.log(response)
        }).catch(err => {
          console.log(err)
        })
      }
    },

    showChooseCourse() {
      this.ifStudentsJson = false
      this.ifTeacherJson = false
      this.ifCourseJson = false
      this.ifShowInfo = false
      this.ifShowChooseCourse = true

      this.theInfo = []
      request.post('/node/selectAllCourse', {}).then(response => {
        this.theInfo = response;
        this.allCourse = response;

        console.log(this.allCourse)

      }).catch(err => {
        console.log(err)
      })
    },
    getCourseBySid() {
      request.post('/node/getStudentCourseBySidInJsonConfirm', {
        sid: this.SID
      }).then(response => {
        console.log(response)
        this.course.length = 1
        for (var i = 0; i < response.length; i += 1) {
          var l = this.course.length
          this.course[l] = []
          this.course[l].cid = response[i].cid
          for (var j = 0; j < this.theInfo.length; j += 1) {
            if (response[i].cid === this.theInfo[j].cid) {
              this.course[l].name = this.theInfo[j].name
            }
          }
        }
        console.log(this.course)
        this.$forceUpdate()
      }).catch(err => {
        console.log(err)
      })
    },

    clickTheCourse(cid) {
      this.choosedCourseId = cid;
    },

    chooseCourse() {
      request.post('/node/addDataToStudentCourseInJsonConfirm', {
        cid: this.choosedCourseId,
        sid: this.SID
      }).then(response => {
        console.log(response)
        this.getCourseBySid()
      }).catch(err => {
        console.log(err)
      })
    },

    getAllCourseNameInStudentCourse() {
      this.ifShowBasicInfo()
      request.post('/node/selectAllCourse', {}).then(response => {
        this.allCourse = response;
        console.log(111)
        console.log(this.allCourse)
        request.post('/node/getAllCourseNameInStudentCourse', {}).then(response1 => {
          this.theInfo = response1

          for (var i = 0; i < this.theInfo.length; i++) {
            var l = this.course.length
            this.course[l] = []
            this.course[l].cid = this.theInfo[i]
            for (var j = 0; j < this.allCourse.length; j++) {
              if (this.course[l].cid === this.allCourse[j].cid) {
                this.course[l].name = this.allCourse[j].name
              }
            }
          }

          this.$forceUpdate()
          console.log(2222)
          console.log(this.theInfo)
          console.log(this.course)
        }).catch(err => {
          console.log(err)
        })
      }).catch(err => {
        console.log(err)
      })


    },
    getStudent10InAverage() {
      this.ifShowBasicInfo()
      request.post('/node/getStudent10InAverage', {}).then(response => {
        this.theInfo = response
        console.log(response)
      }).catch(err => {
        console.log(err)
      })
    },
    deleteCourse(sid,cid){
      request.post('/node/deleteChoosecourse', {
        sid:this.SID,
        cid:cid
      }).then(response => {
        console.log(response)
        this.getCourseBySid()
      }).catch(err => {
        console.log(err)
      })
    }


  },

  mounted() {

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

.fade-enter {
  opacity: 0;
}

.fade-enter-active {
  transition: opacity 1s;
}

.fade-leave-to {
  opacity: 0;
}

.fade-leave-active {
  transition: opacity 1s;
}


</style>
