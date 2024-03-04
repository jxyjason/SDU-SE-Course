package com.example.demo.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("student_course")
public class StudentCourse {
    @Id
    private ObjectId _id;

    private String SID;
    private String CID;
    private double SCORE;
    private String TID;


}
