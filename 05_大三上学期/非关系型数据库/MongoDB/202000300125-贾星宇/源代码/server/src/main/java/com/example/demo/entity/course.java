package com.example.demo.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("course")
public class course {
     @Id
     private ObjectId _id;
     private String CID;
     private String CREDIT;
     private String FCID;
     private String NAME;
}
