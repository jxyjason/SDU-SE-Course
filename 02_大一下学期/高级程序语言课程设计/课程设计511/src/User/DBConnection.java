package User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private Connection conn;
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/user?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false";
    private String username = "root";
    private String password = "1111";

    public DBConnection(){
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
        DBConnection dc = new DBConnection();
        dc.getConnection();
        System.out.print("连接数据库成功");
    }
















}
