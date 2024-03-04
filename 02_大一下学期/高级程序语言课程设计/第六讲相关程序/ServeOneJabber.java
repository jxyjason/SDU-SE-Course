package SocketTest;

import java.io.*;
import java.net.*;

class ServeOneJabber extends Thread {
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;

	public ServeOneJabber(Socket s) throws IOException {
		socket = s;
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		// Enable auto-flush:
		out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
				.getOutputStream())), true);
		// If any of the above calls throw an
		// exception, the caller is responsible for
		// closing the socket. Otherwise the thread
		// will close it.
		start(); // Calls run()
	}

	public void run() {
		try {
			while (true) {
				String str = in.readLine();
				if (str.equals("END"))
					break;
				System.out.println("Echoing: " + str);
				out.println(str);
			}
			System.out.println("closing...");
		} catch (IOException e) {
			System.err.println("IO Exception");
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				System.err.println("Socket not closed");
			}
		}
	}
}
