package com.example.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.demo.config.Result;
import com.example.demo.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin


@RequestMapping(value = "/node")
public class NodeController {


    @Autowired
    private MongoTemplate mongoTemplate;


    @RequestMapping(value = "/selectStudentsByAgeLe20", method = RequestMethod.POST)
    public List<student> selectStudentsByAgeLt20() {
        Query query = new Query(Criteria.where("AGE").lt(20));
        List<student> students = mongoTemplate.find(query, student.class);
        return students;
    }

    @RequestMapping(value = "/selectStudentsByAgeLe20AndRuanJian", method = RequestMethod.POST)
    public List<student> selectStudentsByAgeLe20AndRuanJian() {
        Query query = new Query(Criteria.where("AGE").lt(20).and("DNAME").is("软件学院"));
        List<student> students = mongoTemplate.find(query, student.class);
        return students;
    }

    @RequestMapping(value = "/selectAllStudents", method = RequestMethod.POST)
    public List<student> selectAllStudents() {
        List<student> students = mongoTemplate.findAll(student.class);
        return students;
    }

    @RequestMapping(value = "/selectAllCourse", method = RequestMethod.POST)
    public List<course> selectAllCourse() {
        List<course> courses = mongoTemplate.findAll(course.class);
        return courses;
    }

    @RequestMapping(value = "/selectAllTeacher", method = RequestMethod.POST)
    public List<teacher> selectAllTeacher() {
        List<teacher> teachers = mongoTemplate.findAll(teacher.class);
        return teachers;
    }

    @RequestMapping(value = "/selectAllStudentsNameSex", method = RequestMethod.POST)
    public List<student> selectAllStudentsNameSex() {
        Query query = new Query();
        query.fields().include("NAME").include("SEX");
        List<student> students = mongoTemplate.find(query, student.class);
        return students;
    }

    @RequestMapping(value = "/addDataToStudentInJson", method = RequestMethod.POST)
    public Result<?> addDataToStudentInJson(@RequestBody student students) {
        List<student> findStudent = mongoTemplate.find(new Query(Criteria.where("SID").is(students.getSID())), student.class);
        if (findStudent.isEmpty()) mongoTemplate.insert(students, "student");
        else {
            Query query = new Query(Criteria.where("SID").is(students.getSID()));
            Update update = new Update().set("NAME", students.getNAME()).set("SEX", students.getSEX()).set("AGE", students.getAGE()).
                    set("BIRTHDAY", students.getBIRTHDAY()).set("DNAME", students.getDNAME()).set("CLASS", students.getCLASS());
            mongoTemplate.updateFirst(query, update, student.class);
        }
        return Result.success();
    }

    @RequestMapping(value = "/addDataToTeacherInJsonConfirm", method = RequestMethod.POST)
    public Result<?> addDataToTeacherInJsonConfirm(@RequestBody teacher teachers) {
        List<teacher> findTeacher = mongoTemplate.find(new Query(Criteria.where("TID").is(teachers.getTID())), teacher.class);
        if (findTeacher.isEmpty()) mongoTemplate.insert(teachers, "teacher");
        else {
            Query query = new Query(Criteria.where("TID").is(teachers.getTID()));
            Update update = new Update().set("NAME", teachers.getNAME()).set("SEX", teachers.getSEX()).set("AGE", teachers.getAGE()).
                    set("DNAME", teachers.getDNAME());
            mongoTemplate.updateFirst(query, update, teacher.class);
        }
        return Result.success();
    }

    @RequestMapping(value = "/addDataToCourseInJsonConfirm", method = RequestMethod.POST)
    public Result<?> addDataToCourseInJsonConfirm(@RequestBody course courses) {

        List<course> findCourse = mongoTemplate.find(new Query(Criteria.where("CID").is(courses.getCID())), course.class);
        if (findCourse.isEmpty()) mongoTemplate.insert(courses, "course");
        else {
            Query query = new Query(Criteria.where("CID").is(courses.getCID()));
            Update update = new Update().set("NAME", courses.getNAME()).set("CREDIT", courses.getCREDIT()).set("FCID", courses.getFCID());
            mongoTemplate.updateFirst(query, update, course.class);
        }
        return Result.success();
    }

    @RequestMapping(value = "/addDataToStudentCourseInJsonConfirm", method = RequestMethod.POST)
    public Result<?> addDataToStudentCourseInJsonConfirm(@RequestBody StudentCourse studentCourse) {
        List<TeacherCourse> teacherCourses = mongoTemplate.find(new Query(Criteria.where("CID").is(studentCourse.getCID())), TeacherCourse.class);
        studentCourse.setTID(teacherCourses.get(0).getTID());
        mongoTemplate.insert(studentCourse, "student_course");
        return Result.success();
    }

    @RequestMapping(value = "/getStudentCourseInJsonConfirm", method = RequestMethod.POST)
    public List<StudentCourse> getStudentCourseInJsonConfirm(@RequestBody StudentCourse studentCourse) {
        return mongoTemplate.findAll(StudentCourse.class);
    }

    @RequestMapping(value = "/getStudentCourseBySidInJsonConfirm", method = RequestMethod.POST)
    public List<StudentCourse> getStudentCourseBySidInJsonConfirm(@RequestBody StudentCourse studentCourse) {
        return mongoTemplate.find(new Query(Criteria.where("SID").is(studentCourse.getSID())), StudentCourse.class);
    }

    @RequestMapping(value = "/addDataToTeacherCourseInJsonConfirm", method = RequestMethod.POST)
    public Result<?> addDataToTeacherCourseInJsonConfirm(@RequestBody TeacherCourse teacherCourse) {
        mongoTemplate.insert(teacherCourse, "teacher_course");
        return Result.success();
    }

    @RequestMapping(value = "/addDataInExcelStudent", method = RequestMethod.POST)
    public Result<?> addDataInExcelStudent(@RequestBody String whichdata) {
        String sp = whichdata.split("whichdata")[1];
        String a = sp.substring(3, sp.length() - 2);
        JSONObject jsonObject = JSONObject.parseObject(a);
        mongoTemplate.insert(a, "student");
        return Result.success();
    }

    @RequestMapping(value = "/getAllCourseNameInStudentCourse", method = RequestMethod.POST)
    public List<StudentCourse> getAllCourseNameInStudentCourse(@RequestBody String whichdata) {
        List<StudentCourse> studentCourse = mongoTemplate.findDistinct(new Query(), "CID", "student_course", StudentCourse.class);
        return studentCourse;
    }

    @RequestMapping(value = "/getStudent10InAverage", method = RequestMethod.POST)
    public List<student> getStudent10InAverage(@RequestBody String whichdata) {
        //聚合函数查询统计信息
        Criteria criteria = Criteria.where("SCORE").lt(1000);
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(criteria),
                //按分类名称 统计 销售数量
                Aggregation.group("SID").avg("SCORE").as("avgscore"),
                //按照total降序
                Aggregation.sort(Sort.Direction.DESC, "avgscore"));
        AggregationResults<Map> aggregationResults = mongoTemplate.aggregate(aggregation, StudentCourse.class,Map.class);
        List<Map> list = aggregationResults.getMappedResults();
        List<student> result = new ArrayList<student>();
        for (int i=0;i<10;i++){
            List<student> student1 = mongoTemplate.find(new Query(Criteria.where("SID").is(list.get(i).get("_id"))),student.class);
            student1.get(0).setNAME(student1.get(0).getNAME()+", 平均分："+list.get(i).get("avgscore"));
            result.add(student1.get(0));
        }
        return result;
    }


    @RequestMapping(value = "/deleteChoosecourse", method = RequestMethod.POST)
    public Result<?> deleteChoosecourse(@RequestBody StudentCourse studentCourse) {
        mongoTemplate.remove(new Query(Criteria.where("SID").is(studentCourse.getSID()).and("CID").is(studentCourse.getCID())),StudentCourse.class);
        return Result.success();
    }


//    mongoTemplate.remove(new Query(Criteria.where("id").is(params.get("id"))),User.class,collectionName);

}
