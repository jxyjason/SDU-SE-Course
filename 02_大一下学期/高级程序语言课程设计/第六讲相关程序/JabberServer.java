package SocketTest;

import java.io.*;
import java.net.*;

public class JabberServer {
	// Choose a port outside of the range 1-1024:
	static final int PORT = 8080;

	public static void main(String[] args) throws IOException {
		ServerSocket s = new ServerSocket(PORT);
		System.out.println("Started: " + s);
		try {
			// Blocks until a connection occurs:
			Socket socket = s.accept();
			try {
				System.out.println("Connection accepted: " + socket);
				// 输入流，读取客户端信息
				BufferedReader in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				// Output is automatically flushed by PrintWriter:
				// 输出流，向客户端写信息
				PrintWriter out = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(socket.getOutputStream())), true);
				while (true) {
					String str = in.readLine();
					if (str.equals("END"))
						break;
					System.out.println("Echoing: " + str);
					out.println(str);
				}
				// Always close the two sockets...
			} finally {
				System.out.println("closing...");
				socket.close();
			}
		} finally {
			s.close();
		}
	}
}
