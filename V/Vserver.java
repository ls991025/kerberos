package V;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Vserver {
	public static final int PORT=15421;
	public Vserver(){	
	}
	public void run()
	{
		Vui a =new Vui();
		a.ini();
		ServerSocket serverSocket;
		Socket client;
		try {
			//����ServerSocket���󣬼���������PORT�˿�
			serverSocket=new ServerSocket(PORT);
			while(true){
				//����socket�׽��֣�����ͻ�����������
				client=serverSocket.accept();
				Vrunnable my = new Vrunnable(client,a.ta);
				Thread thread = new Thread(my);
				thread.start();	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args)
	{

		Vserver server=new Vserver();
		server.run();
	}
	
}
