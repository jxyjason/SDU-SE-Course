package DatabaseTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @ClassName: DbConnection
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Dangzhang
 * @date 2020-3-17 下午5:05:47
 * 
 */
public class DbConnection
{

    private Connection conn;
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/instant?useSSL=false&serverTimezone=UTC";
    private String username = "root";
    private String password = "root";

    public DbConnection()
    {
        try
        {
            Class.forName(driver);
            this.conn = DriverManager.getConnection(url, username, password);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public Connection getConnection()
    {
        return this.conn;
    }

    public static void main(String[] args)
    {
        DbConnection dc = new DbConnection();
        dc.getConnection();
        System.out.print("连接数据库成功");
    }
}
