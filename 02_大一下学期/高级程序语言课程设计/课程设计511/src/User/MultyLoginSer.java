package User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultyLoginSer{
    public MultyLoginSer(){
        run();
    }
    public void run() {
        ServerSocket server = null;
        try {
            server = new ServerSocket(6666);
            try {
                while (true){
                    Socket socket = server.accept();
                    try {
                        new LoginServer(socket);//创建一条线程
                    }catch (IOException e){
                        socket.close();
                    }
                }
            }finally {
                server.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new MultyLoginSer();
    }
}
