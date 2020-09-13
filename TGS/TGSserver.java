package TGS;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TGSserver {
	public static final int PORT=15421;
	public TGSserver(){	
	}
	public void run()
	{
		TGSui a =new TGSui();
		a.ini();
		ServerSocket serverSocket;
		Socket client;
		try {
			//创建ServerSocket对象，监听本机的PORT端口
			serverSocket=new ServerSocket(PORT);
			while(true){
				//创建socket套接字，处理客户端连接请求
				client=serverSocket.accept();
				TGSrunnable my = new TGSrunnable(client,a.ta);
				Thread thread = new Thread(my);
				thread.start();	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args)
	{

		TGSserver server=new TGSserver();
		server.run();
	}
	
}
