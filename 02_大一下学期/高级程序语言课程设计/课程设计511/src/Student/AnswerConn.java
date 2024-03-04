package Student;

import Teacher.PaperConn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AnswerConn {
    private Connection conn;
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/studentanswer?useSSL=false&serverTimezone=UTC";
    private String username = "root";
    private String password = "1111";

    public AnswerConn(){
        try {
            Class.forName(driver);
            this.conn = DriverManager.getConnection(url,username,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
    public Connection getConnection(){
        return this.conn;
    }

    public static void main(String[] args){
        AnswerConn dc = new AnswerConn();
        dc.getConnection();
        System.out.print("连接数据库成功");
    }
}
