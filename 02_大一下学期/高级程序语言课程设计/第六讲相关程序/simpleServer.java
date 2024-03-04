package SocketTest;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class simpleServer {

    public static void main(String[] args){
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            int count = 0;// 记录客户端的数量
            System.out.println("服务器启动，等待客户端的连接。。。");
            Socket socket = null;
            while(true){
                socket=serverSocket.accept();
                ++count;
                Thread serverHandleThread=new Thread(new ServerHandleThread(socket));
                serverHandleThread.setPriority(4);
                serverHandleThread.start();
                System.out.println("上线的客户端有" + count + "个！");
                InetAddress inetAddress = socket.getInetAddress();
                System.out.println("当前客户端的IP地址是："+inetAddress.getHostAddress());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}