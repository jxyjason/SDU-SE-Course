package SocketTest;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;





public class ServerHandleThread implements Runnable{
    Socket socket=null;
     
    public ServerHandleThread(Socket socket) {
        super();
        this.socket = socket;
    }
    @Override
    public void run() {
        // TODO Auto-generated method stub
        OutputStream os = null;
        PrintWriter pw = null;
        try {
            InputStream is = socket.getInputStream();
            ObjectInputStream ois=new ObjectInputStream(is);
            //readObject()方法必须保证服务端和客户端的User包名一致，要不然会出现找不到类的错误
            System.out.println("客户端发送的对象：" + (User) ois.readObject());
            socket.shutdownInput();// 禁用套接字的输入流
            os=socket.getOutputStream();
            pw=new PrintWriter(os);
            pw.println("欢迎登录！");
            pw.flush();
            socket.shutdownOutput();
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try{
                if(pw!=null){
                    pw.close();
                }
                if(os!=null){
                    os.close();
                }
                if(socket!=null){
                    socket.close();
                }
            }catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }

        }
    }
    
} 