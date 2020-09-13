package C;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import DES.UseDES;
import RSA.UseRSA;

public class Authenticator implements ActionListener{
	public static final int PORT=15421;
	Container dialogPane;
	JFrame Cd;				//client的第一层UI
	JFrame d;
	JLabel label1=new JLabel();
	JLabel label2=new JLabel();
	JLabel label3=new JLabel();
	JLabel label4=new JLabel();
	JButton button1;
	JButton button2;
	JButton button3;
	//JButton button4;
	JButton button5;
	JButton button6;
	JScrollPane jsp;
	JTextArea ta;
	JTextArea idv;
	JTextField text;
	JPasswordField text1;
	static String timeStampas = new String();
	static String timeStamptgs = new String();
	// 时间戳。
	private static String ticketas = new String();
	private static String tickettgs = new String();
	// 票据
	private static String keyctgs = new String();
	private static String keycv = new String();
	// 对称密钥
	private static String ID = new String();
	private static String KEY = new String();
	//账号密码的hash
	
	
	Socket AS_clientSocket;		//用于发送请求连接并接受rsa_n,rsa_e
	String rsa_n=new String();
	String rsa_e=new String();
	
	public Authenticator()
	{
		
	}
	public Authenticator(String Idv,JFrame Cd){
		super();
		this.Cd=Cd;					//客户端第一层UI，用于此层UI关闭时启动第一层
		d=new JFrame();                      //新建一对话框
		d.setTitle("Authenticator");            //设置标题
		dialogPane=d.getContentPane();
		dialogPane.setLayout(null);
		dialogPane.setBackground(Color.white);
		d.setBounds(700,100,620,520);          //设置窗口的大小
		label1.setText("                             认证过程                             ");
		label1.setFont(new Font("",1,20));   //字体设计
		label1.setBounds(25, 1, 620, 80);      //设置显示信息的位置
		label2.setText("ID_c:");
		label2.setBounds(25, 130, 40, 25);
		label3.setText("key:");
		label3.setBounds(25, 180, 40, 25);
		label4.setText("ID_v:");
		label4.setBounds(25, 80, 40, 25);
		text=new JTextField();
		text.setBounds(70, 130, 110, 25);
		text1=new JPasswordField();				//密码输入框
		text1.setBounds(70, 180, 110, 25);
		button1=new JButton("连接AS");
		button1.addActionListener(this);
		button1.setBounds(25,220,150,25);
		button2=new JButton("连接TGS");
		button2.addActionListener(this);
		button2.setBounds(25,260,150,25);
		button3=new JButton("连接V");
		button3.addActionListener(this);
		button3.setBounds(25,300,150,25);
		/*button4=new JButton("一键连接");
		button4.addActionListener(this);
		button4.setBounds(25,340,150,25);*/
		button5=new JButton("注册");
		button5.addActionListener(this);
		button5.setBounds(25,380,150,25);
		button6=new JButton("返回");
		button6.addActionListener(this);
		button6.setBounds(25,420,150,25);
		ta=new JTextArea();
		ta.setFont(new Font("",1,14));
		ta.setBorder(BorderFactory.createLineBorder(Color.black));
		ta.setLineWrap(true);
		jsp=new JScrollPane(ta);
		jsp.setBounds(200, 80, 360, 360);
		idv=new JTextArea();
		idv.setBounds(70, 80, 110, 25);
		idv.setText(Idv);
		idv.setBorder(BorderFactory.createLineBorder(Color.black));
		dialogPane.add(label1);
		dialogPane.add(label2);
		dialogPane.add(label3);
		dialogPane.add(label4);
		dialogPane.add(text);
		dialogPane.add(text1);
		dialogPane.add(button1);
		dialogPane.add(button2);
		dialogPane.add(button3);
		//dialogPane.add(button4);
		dialogPane.add(button5);
		dialogPane.add(button6);
		dialogPane.add(jsp);
		dialogPane.add(idv);
		d.setVisible(true);
		
		
		//从AS获取公钥
		String Server="127.0.0.1";
		//创建socket套接字，连接本机的PORT端口
		
		BufferedReader sin = null;
		PrintWriter sout = null;
		try {
			AS_clientSocket=new Socket(Server,PORT);
		//创建缓冲区对象，用于读取服务器发送的信息
			sin=new BufferedReader(new InputStreamReader(AS_clientSocket.getInputStream()));
		//创建缓冲区对象，用于向服务器发送信息
			sout=new PrintWriter(AS_clientSocket.getOutputStream());
			ta.append("连接成功\n");
			sout.println("1111111111111000");
			sout.flush();
			
			rsa_n=sin.readLine();
			rsa_e=sin.readLine();
			ta.append("公钥：\nn:"+rsa_n+"\ne:"+rsa_e+"\n");
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	//与AS进行通信
	public void Asrun()
	{
		ID=Hash.md5(text.getText());
		KEY=Hash.md5(text1.getText());//对输入的账号密码hash
		
		ta.append("连接AS中......\n");
		String Server="127.0.0.1";
		//创建socket套接字，连接本机的PORT端口
		Socket clientSocket;
		BufferedReader sin = null;
		PrintWriter sout = null;
		try {
			clientSocket=new Socket(Server,PORT);
		//创建缓冲区对象，用于读取客户端发送的信息
			sin=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		//创建缓冲区对象，用于向服务器发送信息
			sout=new PrintWriter(clientSocket.getOutputStream());
			ta.append("套接字创建成功\n");
		} catch (IOException e) {
			ta.append(e.getMessage()+"\n");
		}
		
		//client到AS的封包
		String s3 = null;
		String string;
		String s4=CtoAS(ID);
		sout.println(s4);
		sout.flush();
		ta.append("包发送成功，等待AS回复......\n");
		try {
			s3=sin.readLine();
			if(s3!=null)
			{
				ta.append("收到AS回复\n");
				
				//获取数据包
				string = Util.binaryToString(s3);
				ta.append("收到:"+string+"\n");
				String head =string.substring(0, 16);
				string =string.substring(16, string.length());
				if(head.equals("0001000000000000"))
				{
					ta.append("认证失败!\n");
				}
				else
				{
					s4 = UseDES.decryMessage(string, KEY);//用密码的hash值作为8个字符密钥解密
					ta.append("解密:"+s4+"\n");	/**s4为AS发来报文，携带签名，可留用作日后防AS抵赖的证明
												*/
					
					s4=head+s4;
					DataPacket dataPacket=new DataPacket();
					dataPacket.parseMessage(s4);
					ticketas=dataPacket.getTicket();
					timeStampas=dataPacket.getTimeStamp();
					keyctgs=dataPacket.getKey();
					ta.append("keyctgs:"+keyctgs+"\n");
					
					String ASsig=dataPacket.getASsig();//AS签名
					UseRSA rsa=new UseRSA();
					ASsig=rsa.decrypt(ASsig,rsa_n, rsa_e);
					ta.append("ASsig:"+ASsig+"\n");
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//与TGS进行通信
	public void Tgsrun()
	{
		ta.append("连接TGS中......\n");
		String Server="127.0.0.1";
		//创建socket套接字，连接本机的PORT端口
		Socket clientSocket = null;
		BufferedReader sin = null;
		PrintWriter sout = null;
		try {
			clientSocket=new Socket(Server,PORT);
		//创建缓冲区对象，用于读取服务器发送的信息
			sin=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		//创建缓冲区对象，用于向服务器发送信息
			sout=new PrintWriter(clientSocket.getOutputStream());
			ta.append("套接字创建成功\n");
		} catch (IOException e) {
			ta.append(e.getMessage());
		}
		String s3 = null ;
		InetAddress addr=clientSocket.getLocalAddress();
		String address=addr.toString();
		String s4=CtoTGS(idv.getText(),ticketas,ID,address);
		ta.append("tgs:"+s4+"\n");
		s3=Util.toBinaryString(s4);
		sout.println(s3);
		sout.flush();
		ta.append("包发送乘成功，等待TGS回复......\n");
		try {
			s3=sin.readLine();
			if(s3!=null)
			{
				ta.append("收到TGS回复\n");
				DataPacket dataPacket=new DataPacket();
				//获取数据包
				String string;
				string = Util.binaryToString(s3);
				ta.append("收到:"+string+"\n");
				String head=string.substring(0, 16);
				string=string.substring(16, string.length());
				if(head.equals("0001000000000000"))
				{
					ta.append("认证失败!\n");
				}
				else
				{
					s4 = UseDES.decryMessage(string, keyctgs);
					ta.append("de:"+s4+"\n");
					s4=head+s4;
					dataPacket.parseMessage(s4);
					tickettgs=dataPacket.getTicket();
					timeStamptgs=dataPacket.getTimeStamp();
					keycv=dataPacket.getKey();
				}	
			}
		} catch (IOException e) {
			ta.append(e.getMessage());
		}
	}
	//与V进行通信
	public void Vrun()
	{
		ta.append("连接V中......\n");
		String Server="";
		if(idv.getText().equals("20171001"))		//不同服务器设置ip
			Server = "127.0.0.1";
		if(idv.getText().equals("20171002"))
			Server="127.0.0.1";
		/*if(idv.getText().equals("20161003"))
			Server="127.0.0.1";
		if(idv.getText().equals("20161004"))
			Server="127.0.0.1";*/
		//创建socket套接字，连接本机的PORT端口
		Socket clientSocket = null;
		BufferedReader sin = null;
		PrintWriter sout = null;
		try {
			clientSocket=new Socket(Server,PORT);
		//创建缓冲区对象，用于读取服务器发送的信息
			sin=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		//创建缓冲区对象，用于向服务器发送信息
			sout=new PrintWriter(clientSocket.getOutputStream());
			ta.append("套接字创建成功\n");
		} catch (IOException e) {
			ta.append(e.getMessage());
		}
		//client到V的封包
		String s3;
		InetAddress addr=clientSocket.getLocalAddress();	//本机地址
		String address=addr.toString();
		String s4=CtoV(tickettgs,ID,address);
		ta.append("发送："+s4+"\n");
		s4=Util.toBinaryString(s4);
		sout.println(s4);
		sout.flush();
		ta.append("包发送成功，等待V回复......\n");
		try {
			s3=sin.readLine();
			if(s3!=null)
			{
				ta.append("收到V回复\n");
				//获取数据包
				String string;
				string = Util.binaryToString(s3);
				ta.append("收到:"+string+"\n");
				DataPacket dp = new DataPacket();
				dp.parseMessage1(string, keycv);
				String tmp = timeStamptgs;
				BigInteger b = new BigInteger(tmp).add(new BigInteger("1"));	//TimeStamp+1
				tmp = b.toString();
				if(!dp.getTimeStamp().equals(tmp))
				{
					ta.append("认证失败!\n");
				}else{
					ta.append("认证成功!\n");
					ta.append("与V会话中......\n");
					
					switch(idv.getText())				//不同服务调用不同程序
					{
					case "20171001":
						C_juzhen v1=new C_juzhen(clientSocket,keycv,ta,ID);
						v1.ini();
						break;
					case "20171002":
						C_Search v2=new C_Search(clientSocket,keycv,ta,ID);
						v2.ini();
						break;
					case "20171003":
						C_GetInfo v3=new C_GetInfo(clientSocket,keycv,ta);
						v3.ini();
						sout.flush();
						break;
					case "20171004":
						ta.append("server4:\n");
						break;
					default:
						ta.append("vid错误\n");					//服务id错误，向服务端发送“！”，及时释放资源
						String er=Util.toBinaryString("!");
						sout.println(er);
						sout.flush();
						break;
					}						
				}			
			}
		} catch (IOException e) {
			ta.append(e.getMessage());
		}
	}
	
	//按钮点击事件
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd=e.getActionCommand();
		if(cmd.equals("连接AS")){
			Asrun();
		}
		if(cmd.equals("连接TGS")){
			Tgsrun();
		}
		if(cmd.equals("连接V")){
			Vrun();
		}
		/*if(cmd.equals("一键连接"))
		{
			Asrun();
			Tgsrun();
			Vrun();
		}*/
		if(cmd.equals("注册")){
			new C_Login(rsa_n,rsa_e);
		}
		if(cmd.equals("返回")){
			d.dispose();			//认证服务界面关闭
			Cd.setVisible(true);	//选择服务界面打开
		}
	}
	
	// C to AS封包
	public static String CtoAS(String str){
		String CID = str;    //对账号进行hash
		String TGSID =Cpackage.GetTGSID();
		String Timestamp =Cpackage.GetTS();
		String head = "0001011100000000";
		String Package = head + CID + TGSID + Timestamp;	
		return Package;
	}
	
	// C to TGS封包
	public  String CtoTGS(String str,String str1,String str2,String str3){
		String IDV = str;
		String ticket = str1;
		String CID = str2 ;
		String address=Cpackage.GetAD(str3);
		String  TS2 = timeStampas;
		String head ="0001001001010000";
		String Authen = CID + address + TS2;
		Authen=UseDES.encryMessage(Authen, keyctgs);
		String Package = head +IDV + ticket+ Authen ;
		return Package;
	}
	
	//C to V认证过程封包
	public static String CtoV(String str,String str1,String str2){
		String head ="0001000001010000";
		String ticket = str;
		String  TS3 = timeStamptgs;
		String address=Cpackage.GetAD(str2);
		String Authen = str1+address+TS3;
		Authen=UseDES.encryMessage(Authen, keycv);
		String Package = head + ticket+ Authen ;
		return Package;
		
	}
}
