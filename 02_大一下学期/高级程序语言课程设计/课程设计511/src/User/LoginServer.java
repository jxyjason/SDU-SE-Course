package User;

import java.io.*;
import java.net.Socket;
import java.sql.*;

public class LoginServer extends Thread{
    DBConnection dbConn = new DBConnection();
    Connection conn = dbConn.getConnection();

    Socket socket;
    public LoginServer(Socket s) throws IOException {
        socket = s;
        start();
    }


    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            PrintWriter pw = new PrintWriter(bw,true);
            while (true) {
                String receive;
                while ((receive = br.readLine())!=null) {
                    String[] get = receive.split(" ");//fieldId.getText()+" "+fieldPass.getText()+" "+isTeacher;
                    int judge = check(get[0], get[1], Integer.parseInt(get[2]));
                    pw.println(judge);
                }
            }
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
     }

    public int check(String tId,String tPassword,int tIsTea){
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM user.new_table";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                String id = rs.getString(1);
                String pass = rs.getString(3);
                int isTea = rs.getInt(5);
                if(tId.equals(id)&&tPassword.equals(pass)&&
                        (tIsTea==isTea)){
                    return 1;
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }


    public static void main(String[] args) throws IOException {

    }






}
