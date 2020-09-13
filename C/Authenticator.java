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
	JFrame Cd;				//client�ĵ�һ��UI
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
	// ʱ�����
	private static String ticketas = new String();
	private static String tickettgs = new String();
	// Ʊ��
	private static String keyctgs = new String();
	private static String keycv = new String();
	// �Գ���Կ
	private static String ID = new String();
	private static String KEY = new String();
	//�˺������hash
	
	
	Socket AS_clientSocket;		//���ڷ����������Ӳ�����rsa_n,rsa_e
	String rsa_n=new String();
	String rsa_e=new String();
	
	public Authenticator()
	{
		
	}
	public Authenticator(String Idv,JFrame Cd){
		super();
		this.Cd=Cd;					//�ͻ��˵�һ��UI�����ڴ˲�UI�ر�ʱ������һ��
		d=new JFrame();                      //�½�һ�Ի���
		d.setTitle("Authenticator");            //���ñ���
		dialogPane=d.getContentPane();
		dialogPane.setLayout(null);
		dialogPane.setBackground(Color.white);
		d.setBounds(700,100,620,520);          //���ô��ڵĴ�С
		label1.setText("                             ��֤����                             ");
		label1.setFont(new Font("",1,20));   //�������
		label1.setBounds(25, 1, 620, 80);      //������ʾ��Ϣ��λ��
		label2.setText("ID_c:");
		label2.setBounds(25, 130, 40, 25);
		label3.setText("key:");
		label3.setBounds(25, 180, 40, 25);
		label4.setText("ID_v:");
		label4.setBounds(25, 80, 40, 25);
		text=new JTextField();
		text.setBounds(70, 130, 110, 25);
		text1=new JPasswordField();				//���������
		text1.setBounds(70, 180, 110, 25);
		button1=new JButton("����AS");
		button1.addActionListener(this);
		button1.setBounds(25,220,150,25);
		button2=new JButton("����TGS");
		button2.addActionListener(this);
		button2.setBounds(25,260,150,25);
		button3=new JButton("����V");
		button3.addActionListener(this);
		button3.setBounds(25,300,150,25);
		/*button4=new JButton("һ������");
		button4.addActionListener(this);
		button4.setBounds(25,340,150,25);*/
		button5=new JButton("ע��");
		button5.addActionListener(this);
		button5.setBounds(25,380,150,25);
		button6=new JButton("����");
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
		
		
		//��AS��ȡ��Կ
		String Server="127.0.0.1";
		//����socket�׽��֣����ӱ�����PORT�˿�
		
		BufferedReader sin = null;
		PrintWriter sout = null;
		try {
			AS_clientSocket=new Socket(Server,PORT);
		//�����������������ڶ�ȡ���������͵���Ϣ
			sin=new BufferedReader(new InputStreamReader(AS_clientSocket.getInputStream()));
		//�������������������������������Ϣ
			sout=new PrintWriter(AS_clientSocket.getOutputStream());
			ta.append("���ӳɹ�\n");
			sout.println("1111111111111000");
			sout.flush();
			
			rsa_n=sin.readLine();
			rsa_e=sin.readLine();
			ta.append("��Կ��\nn:"+rsa_n+"\ne:"+rsa_e+"\n");
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	//��AS����ͨ��
	public void Asrun()
	{
		ID=Hash.md5(text.getText());
		KEY=Hash.md5(text1.getText());//��������˺�����hash
		
		ta.append("����AS��......\n");
		String Server="127.0.0.1";
		//����socket�׽��֣����ӱ�����PORT�˿�
		Socket clientSocket;
		BufferedReader sin = null;
		PrintWriter sout = null;
		try {
			clientSocket=new Socket(Server,PORT);
		//�����������������ڶ�ȡ�ͻ��˷��͵���Ϣ
			sin=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		//�������������������������������Ϣ
			sout=new PrintWriter(clientSocket.getOutputStream());
			ta.append("�׽��ִ����ɹ�\n");
		} catch (IOException e) {
			ta.append(e.getMessage()+"\n");
		}
		
		//client��AS�ķ��
		String s3 = null;
		String string;
		String s4=CtoAS(ID);
		sout.println(s4);
		sout.flush();
		ta.append("�����ͳɹ����ȴ�AS�ظ�......\n");
		try {
			s3=sin.readLine();
			if(s3!=null)
			{
				ta.append("�յ�AS�ظ�\n");
				
				//��ȡ���ݰ�
				string = Util.binaryToString(s3);
				ta.append("�յ�:"+string+"\n");
				String head =string.substring(0, 16);
				string =string.substring(16, string.length());
				if(head.equals("0001000000000000"))
				{
					ta.append("��֤ʧ��!\n");
				}
				else
				{
					s4 = UseDES.decryMessage(string, KEY);//�������hashֵ��Ϊ8���ַ���Կ����
					ta.append("����:"+s4+"\n");	/**s4ΪAS�������ģ�Я��ǩ�������������պ��AS������֤��
												*/
					
					s4=head+s4;
					DataPacket dataPacket=new DataPacket();
					dataPacket.parseMessage(s4);
					ticketas=dataPacket.getTicket();
					timeStampas=dataPacket.getTimeStamp();
					keyctgs=dataPacket.getKey();
					ta.append("keyctgs:"+keyctgs+"\n");
					
					String ASsig=dataPacket.getASsig();//ASǩ��
					UseRSA rsa=new UseRSA();
					ASsig=rsa.decrypt(ASsig,rsa_n, rsa_e);
					ta.append("ASsig:"+ASsig+"\n");
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//��TGS����ͨ��
	public void Tgsrun()
	{
		ta.append("����TGS��......\n");
		String Server="127.0.0.1";
		//����socket�׽��֣����ӱ�����PORT�˿�
		Socket clientSocket = null;
		BufferedReader sin = null;
		PrintWriter sout = null;
		try {
			clientSocket=new Socket(Server,PORT);
		//�����������������ڶ�ȡ���������͵���Ϣ
			sin=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		//�������������������������������Ϣ
			sout=new PrintWriter(clientSocket.getOutputStream());
			ta.append("�׽��ִ����ɹ�\n");
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
		ta.append("�����ͳ˳ɹ����ȴ�TGS�ظ�......\n");
		try {
			s3=sin.readLine();
			if(s3!=null)
			{
				ta.append("�յ�TGS�ظ�\n");
				DataPacket dataPacket=new DataPacket();
				//��ȡ���ݰ�
				String string;
				string = Util.binaryToString(s3);
				ta.append("�յ�:"+string+"\n");
				String head=string.substring(0, 16);
				string=string.substring(16, string.length());
				if(head.equals("0001000000000000"))
				{
					ta.append("��֤ʧ��!\n");
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
	//��V����ͨ��
	public void Vrun()
	{
		ta.append("����V��......\n");
		String Server="";
		if(idv.getText().equals("20171001"))		//��ͬ����������ip
			Server = "127.0.0.1";
		if(idv.getText().equals("20171002"))
			Server="127.0.0.1";
		/*if(idv.getText().equals("20161003"))
			Server="127.0.0.1";
		if(idv.getText().equals("20161004"))
			Server="127.0.0.1";*/
		//����socket�׽��֣����ӱ�����PORT�˿�
		Socket clientSocket = null;
		BufferedReader sin = null;
		PrintWriter sout = null;
		try {
			clientSocket=new Socket(Server,PORT);
		//�����������������ڶ�ȡ���������͵���Ϣ
			sin=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		//�������������������������������Ϣ
			sout=new PrintWriter(clientSocket.getOutputStream());
			ta.append("�׽��ִ����ɹ�\n");
		} catch (IOException e) {
			ta.append(e.getMessage());
		}
		//client��V�ķ��
		String s3;
		InetAddress addr=clientSocket.getLocalAddress();	//������ַ
		String address=addr.toString();
		String s4=CtoV(tickettgs,ID,address);
		ta.append("���ͣ�"+s4+"\n");
		s4=Util.toBinaryString(s4);
		sout.println(s4);
		sout.flush();
		ta.append("�����ͳɹ����ȴ�V�ظ�......\n");
		try {
			s3=sin.readLine();
			if(s3!=null)
			{
				ta.append("�յ�V�ظ�\n");
				//��ȡ���ݰ�
				String string;
				string = Util.binaryToString(s3);
				ta.append("�յ�:"+string+"\n");
				DataPacket dp = new DataPacket();
				dp.parseMessage1(string, keycv);
				String tmp = timeStamptgs;
				BigInteger b = new BigInteger(tmp).add(new BigInteger("1"));	//TimeStamp+1
				tmp = b.toString();
				if(!dp.getTimeStamp().equals(tmp))
				{
					ta.append("��֤ʧ��!\n");
				}else{
					ta.append("��֤�ɹ�!\n");
					ta.append("��V�Ự��......\n");
					
					switch(idv.getText())				//��ͬ������ò�ͬ����
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
						ta.append("vid����\n");					//����id���������˷��͡���������ʱ�ͷ���Դ
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
	
	//��ť����¼�
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd=e.getActionCommand();
		if(cmd.equals("����AS")){
			Asrun();
		}
		if(cmd.equals("����TGS")){
			Tgsrun();
		}
		if(cmd.equals("����V")){
			Vrun();
		}
		/*if(cmd.equals("һ������"))
		{
			Asrun();
			Tgsrun();
			Vrun();
		}*/
		if(cmd.equals("ע��")){
			new C_Login(rsa_n,rsa_e);
		}
		if(cmd.equals("����")){
			d.dispose();			//��֤�������ر�
			Cd.setVisible(true);	//ѡ���������
		}
	}
	
	// C to AS���
	public static String CtoAS(String str){
		String CID = str;    //���˺Ž���hash
		String TGSID =Cpackage.GetTGSID();
		String Timestamp =Cpackage.GetTS();
		String head = "0001011100000000";
		String Package = head + CID + TGSID + Timestamp;	
		return Package;
	}
	
	// C to TGS���
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
	
	//C to V��֤���̷��
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
