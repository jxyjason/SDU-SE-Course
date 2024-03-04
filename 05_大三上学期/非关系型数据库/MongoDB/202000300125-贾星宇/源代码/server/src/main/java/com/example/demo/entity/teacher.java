package com.example.demo.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("teacher")
public class teacher {
    @Id
    private ObjectId _id;
    private String TID;
    private String NAME;
    private String SEX;
    private int AGE;
    private String DNAME;
}
