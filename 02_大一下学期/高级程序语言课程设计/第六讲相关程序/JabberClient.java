package SocketTest;

import java.net.*;
import java.io.*;

public class JabberClient {
	static final int PORT = 8080; // 连接端口

	public static void main(String[] args) throws IOException {
		InetAddress addr = InetAddress.getByName(null);
		System.out.println("addr = " + addr);
		Socket socket = new Socket(addr, PORT);
		try {
			System.out.println("socket = " + socket);
			// 客户端输入流，接收服务器消息
			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			// 客户端输出流，向服务器发消息
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream())), true);
			for (int i = 0; i < 10; i++) {
				out.println("howdy " + i);
				String str = in.readLine();
				System.out.println(str);
			}
			out.println("END");
		} finally {
			System.out.println("closing...");
			socket.close();
		}
	}
}