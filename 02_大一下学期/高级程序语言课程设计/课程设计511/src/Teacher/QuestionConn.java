package Teacher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class QuestionConn {

        private Connection conn;
        private String driver = "com.mysql.cj.jdbc.Driver";
        private String url = "jdbc:mysql://localhost:3306/question?useSSL=false&serverTimezone=UTC";
        private String username = "root";
        private String password = "1111";

        public QuestionConn(){
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
            QuestionConn dc = new QuestionConn();
            dc.getConnection();
            System.out.print("连接数据库成功");
        }
}
