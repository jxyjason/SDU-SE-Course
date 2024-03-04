package SocketTest;

import java.net.*;

public class InetAddrTest {
	public static void main(String[] args) throws UnknownHostException{
		InetAddress ia=InetAddress.getLocalHost(); //获得本机IP地址情况
		System.out.println("主机名："+ia.getHostName());  //得到主机名
		System.out.println("主机地址:"+ia.getHostAddress()); //得到主机地址

	   InetAddress ia2=InetAddress.getByName("LAPTOP-11GSH36C"); //通过主机名获得信息
		System.out.println("主机地址："+ia2.getHostAddress());

		
		InetAddress ia4=InetAddress.getByName("www.163.com"); //根据域名到DNS查询Ip
		System.out.println("163 IP:"+ia4.getHostAddress());

	}
}
