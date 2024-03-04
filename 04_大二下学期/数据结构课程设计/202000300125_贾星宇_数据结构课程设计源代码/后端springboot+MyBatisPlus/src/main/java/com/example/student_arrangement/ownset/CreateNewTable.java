package com.example.student_arrangement.ownset;

import java.sql.*;

public class CreateNewTable {
    public void create(String tableName) throws SQLException, ClassNotFoundException{

        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/student_arrangement?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
                "root", "1111");
        Statement stat = conn.createStatement();
        //获取数据库表名
        ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null);

        // 判断表是否存在，如果存在则什么都不做，否则创建表
        if( rs.next() ){
            return;
        }
        else{
            stat.executeUpdate("CREATE TABLE `student_arrangement`.`"+tableName+"` (\n" +
                    "  `studentid` INT NOT NULL,\n" +
                    "  `studentname` VARCHAR(45) NULL,\n" +
                    "  `score` INT NULL,\n" +
                    "  PRIMARY KEY (`studentid`))\n" +
                    "ENGINE = InnoDB\n" +
                    "DEFAULT CHARACTER SET = utf8mb4\n" +
                    "COLLATE = utf8mb4_unicode_ci;"
            );
        }
        // 释放资源
        stat.close();
        conn.close();
    }
}
