package AS;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.JTextArea;

import DES.UseDES;
import RSA.UseRSA;

public class ASrunnable implements Runnable{
	Socket client;
	String str="";
	JTextArea text=null;
	public ASrunnable(){
		
	}
	public ASrunnable(Socket client,JTextArea text)
	{
		this.client=client;
		this.text = text;
	}
	@Override
	public void run() {
		BufferedReader cin = null;
		PrintWriter cout;
		try{
			//�����������������ڶ�ȡ�ͻ��˷��͵���Ϣ
			cin=new BufferedReader(new InputStreamReader(client.getInputStream()));
			//��������������������ͻ��˷�����Ϣ
			cout=new PrintWriter(client.getOutputStream());
			
			String s =null;
			s = cin.readLine();	
			if(s.equals("1111111111111000"))			//������
			{
				UseRSA rsa=new UseRSA();
				cout.println(rsa.pubkey[0].toString());	//����rsa_n
				cout.flush();
				cout.println(rsa.pubkey[1].toString()); //����rsa_e
				cout.flush();
				s=cin.readLine();			//��������AS��֤��Ϣ
			}
			
			text.append("�յ�:"+s+"\n");
			if(s.charAt(0)=='@')		//ע�ᱨ��
			{
				String c=s.substring(1,s.length());
				UseRSA rsa=new UseRSA();
				s=rsa.decrypt(c, rsa.selfkey[0].toString(), rsa.selfkey[1].toString());
				text.append("---------ע��----------\n");
				String id=s.substring(0, 8);
				String key=s.substring(8,16);
				text.append(id+"\n"+key+"\n");
				linksql z=new linksql();
				if(z.Authen(id)==1)			//��֤id�Ƿ����
					{
					text.append("id�Ѵ���\n");
					cout.println("0");
					cout.flush();
					}	
				else
				{
					z.Linksql(id, key);			//ע��id,key
					text.append("ע��ɹ�\n");
					cout.println("1");
					cout.flush();
				}
				text.append("----------------------\n");
			}
			else		//��֤����
			{
				DataPacket dataPacket = new DataPacket();
				dataPacket.parseMessage(s);
				linksql z=new linksql(); 
				int num= z.Authen(dataPacket.getSrc_id());
				if(num==1){
					str = dataPacket.getSrc_id();
					text.append("�û���"+str+" ��֤�ɹ���\n");	
					String Finaltext=AStoC(dataPacket);
					text.append("Final:"+Finaltext+"\n");
					Finaltext=Util.toBinaryString(Finaltext);
					cout.println(Finaltext);
					cout.flush();
				}else{
					str = dataPacket.getSrc_id();
					text.append("�û���"+str+"��֤ʧ�ܣ�\n");
					String e=Util.toBinaryString("0001000000000000");
					cout.println(e);
					cout.flush();
				}
			}
		}catch(Exception e){
		System.out.println("�������˳�����Ϣ���£�\n"+e.getMessage());
		}		
	}
	/**
	 * ���AS to C
	 * @param dataPacket
	 * @return
	 */
	public String AStoC(DataPacket dataPacket){
		String ktgs = "aaaaaaaa";			//AS TGSĬ����Կ
		String Keyctgs=ASpackage.Createkey(8);
		String TGSID = dataPacket.getDes_id();
		String TS2 =ASpackage.Gettimestamp();
		String Lifetime2 =ASpackage.Lifetime();
		String CID = dataPacket.getSrc_id();
		InetAddress address = client.getInetAddress();
		String AD=address.toString();
		AD=ASpackage.GetAD(AD);
		//System.out.println( AD);
				
		String tickettgs = Keyctgs + CID + AD + TGSID + TS2 + Lifetime2;//��Ҫ����
		this.text.append("tickettgs:"+tickettgs+"\n");
		tickettgs = UseDES.encryMessage(tickettgs, ktgs);
		System.out.println(tickettgs);
		String head = "0001001111101000";
		String text = TGSID + TS2 + Lifetime2 +tickettgs + Keyctgs;//��Ҫ����
		
		UseRSA rsa=new UseRSA();
		String ASsig=Hash.md5(text);
		ASsig=rsa.encrypt(ASsig, rsa.selfkey[0].toString(), rsa.selfkey[1].toString());
		this.text.append("ASsig:"+ASsig+"\n");
		String deASsig=rsa.decrypt(ASsig,rsa.pubkey[0].toString(), rsa.pubkey[1].toString());
		this.text.append("���ܺ��ASsig:"+deASsig+"\n");
		text=text+ASsig;
		
		linksql z=new linksql(); 
		String kc=z.GetUserPassword(dataPacket.getSrc_id());
		this.text.append("text:"+text+"\n");
		text = UseDES.encryMessage(text, kc);
		
		
		String Finaltext = head + text;
		return Finaltext ;
	}

}
