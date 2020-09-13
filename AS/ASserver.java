package AS;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ASserver {
	public static final int PORT=15421;
	public ASserver(){	
	}
	public void run()
	{
		ASui a =new ASui();
		a.ini();
		ServerSocket serverSocket;
		Socket client;
		try {
			//创建ServerSocket对象，监听本机的PORT端口
			serverSocket=new ServerSocket(PORT);
			while(true){
				//创建socket套接字，处理客户端连接请求
				client=serverSocket.accept();
				ASrunnable my = new ASrunnable(client,a.ta);
				Thread thread = new Thread(my);				//创建新线程
				thread.start();	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args)
	{

		ASserver server=new ASserver();
		server.run();
	}
	
}
