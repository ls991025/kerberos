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
			//����ServerSocket���󣬼���������PORT�˿�
			serverSocket=new ServerSocket(PORT);
			while(true){
				//����socket�׽��֣�����ͻ�����������
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
