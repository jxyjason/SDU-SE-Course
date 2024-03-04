package com.example.demo.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("student_course")
public class TeacherCourse {
    @Id
    private ObjectId _id;
    private String TID;
    private String CID;


}
