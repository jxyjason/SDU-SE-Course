//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.sql.*;
//
//public class RegServer {
//    DBConnection dbConn = new DBConnection();
//    Connection conn = dbConn.getConnection();
//
//    ServerSocket server;
//    public RegServer() throws IOException {
//        server = new ServerSocket(7777);
//    }
//
//    public void receiveReg() throws IOException {
//        Socket socket;
//        socket = server.accept();
//
//        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//        PrintWriter pw = new PrintWriter(bw,true);
//        while (true) {
//            String receive;
//            while ((receive = br.readLine())!=null) {
//                String[] get = receive.split(" ");
//                //nameField.getText() + " " + idField.getText() + " " + passField.getText() + " " +isTea;
//                int judge = add(get[0], get[1],get[2],Integer.parseInt(get[3]));
//                pw.println(judge);
//            }
//        }
//    }
//
//    public int add(String tName,String tId,String tPass,int tIsTea){
//        try {
//            String sql1 = "SELECT * FROM user.new_table WHERE id =" +tId;
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(sql1);
//            if (!rs.next()) {//rs.next判断rs是否有第一条数据
//                String sql = "insert into user.new_table (id,name,password,grade,isTeacher) values(?,?,?,?,?)";
//                PreparedStatement pstmt = conn.prepareStatement(sql);
//                pstmt.setInt(1, Integer.parseInt(tId));
//                pstmt.setString(2,tName);
//                pstmt.setString(3, tPass);
//                pstmt.setInt(4, 0);
//                if (tIsTea==1) pstmt.setInt(5, 1);
//                if (tIsTea==0) pstmt.setInt(5, 0);
//                pstmt.executeUpdate();
//                return 1;
//            } else {
//                return 2;
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return 0;
//    }
//
//
//    public static void main(String[] args) throws IOException {
//
//    }
//
//
//}
