package TGS;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JTextArea;

import AS.Util;
import DES.UseDES;

public class TGSrunnable implements Runnable{
	Socket client;
	String str="";
	JTextArea text=null;
	public TGSrunnable(){
		
	}
	public TGSrunnable(Socket client,JTextArea text)
	{
		this.client=client;
		this.text = text;
	}
	@Override
	public void run() {
		BufferedReader cin = null;
		PrintWriter cout;
		try{
			//创建缓冲区对象，用于读取客户端发送的信息
			cin=new BufferedReader(new InputStreamReader(client.getInputStream()));
			//创建缓冲区对象，用于向客户端发送信息
			cout=new PrintWriter(client.getOutputStream());
			
			String s =null;
			s = cin.readLine();
			s=Util.binaryToString(s);
			text.append("收到:"+s+"\n");
			DataPacket dataPacket = new DataPacket();
			dataPacket.parseMessage(s, dataPacket);
			
			//认证id和timestamp
			text.append("Tick_cid"+dataPacket.getTicket().getSrc_id()+"\n");
			text.append("Au_cid"+dataPacket.getAuthenticator().getSrc_id()+"\n");
			text.append("Tick_ts"+dataPacket.getTicket().getTimeStamp()+"\n");
			text.append("Au_ts"+dataPacket.getAuthenticator().getTimeStamp()+"\n");
			if(dataPacket.getTicket().getSrc_id().equals(dataPacket.getAuthenticator().getSrc_id())
				&&dataPacket.getTicket().getTimeStamp().equals(dataPacket.getAuthenticator().getTimeStamp()))
			{
				String Finaltext=TGStoC(dataPacket);
				cout.println(Finaltext);
				cout.flush();
			}else{
				str = dataPacket.getTicket().getSrc_id();
				text.append("用户："+str+"发票失败！\n");
				String e=Util.toBinaryString("0001000000000000");
				cout.println(e);
				cout.flush();
			}					
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("服务器端出错，信息如下：\n"+e.getMessage());
		}		
	}
	/**
	 * 打包TGS to C
	 * @param dataPacket
	 * @return
	 */
	public String TGStoC(DataPacket datapacket){
		
		String mess="";
		String VID = datapacket.getDes_id();
		text.append("VID:"+VID+"\n");
		Ticket Tickettgs= datapacket.getTicket();
		Authen Authen = datapacket.getAuthenticator();
		String Key = Tickettgs.getKey();
		String CIDt = Tickettgs.getSrc_id();
		//String ADt  = Tickettgs.getAd();

		String TS3 = TGSpackage.GetTS();
		linksql a=new linksql();
		String AD = Authen.getAd();
		String CID = Authen.getSrc_id();

		String head ="0001001101100000";
		String Keycv = TGSpackage.Createkey();
		String LifeTime4 = TGSpackage.GetLifetime();
		String ticketv =Keycv + CID + AD + VID + TS3 +LifeTime4 ;
        
		String Keyv="vvvvvvvv";//TGS与V维护的秘钥
		ticketv = UseDES.encryMessage(ticketv,Keyv);
		mess = VID + TS3 + ticketv + Keycv;
		text.append("text:"+mess+"\n");
		mess = head + UseDES.encryMessage(mess, Key);
		text.append("Final:"+mess+"\n");
		mess = Util.toBinaryString(mess);  //转二进制
		str = CIDt;
		text.append("用户："+str+" 发票成功！\n");
		
		return mess;
	}
}
