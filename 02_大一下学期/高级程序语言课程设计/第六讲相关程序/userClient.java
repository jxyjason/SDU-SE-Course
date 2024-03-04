package SocketTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;



  
public class userClient {

	public static void main(String[] args) {
	 
		try {
			Socket socket = new Socket("127.0.0.1", 9999);

		 
			OutputStream os = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(new User(1, "root", "123456"));
			socket.shutdownOutput();

		 
			InputStream is = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String info = null;
			while ((info = br.readLine()) != null) {
				System.out.println("服务器的信息是：" + info);
			}
			socket.shutdownInput();
			oos.close();
			os.close();
			is.close();
			br.close();
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
