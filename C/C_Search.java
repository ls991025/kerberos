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
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import AS.Hash;
import DES.UseDES;
import RSA.UseRSA;

public class C_Search implements ActionListener {
	Container dialogPane;
	JDialog d;
	JLabel label1=new JLabel();
	JLabel label2=new JLabel();
	JButton button1;
	JButton button2;
	JScrollPane jsp;
	JTextArea ta;
	JTextArea tax;
	JTextField text;

	String key;
	String id;
	Socket clientSocket = null;
	BufferedReader sin = null;
	PrintWriter sout = null;
	
	public C_Search(Socket clientSocket,String key,JTextArea tax,String id)
	{
		this.key=key;
		this.clientSocket=clientSocket;
		this.tax=tax;
		this.id=id;
	}
	public void ini(){
		d=new JDialog();                      //�½�һ�Ի���
		d.setTitle("���Ӵʵ�");            //���ñ���
		dialogPane=d.getContentPane();
		dialogPane.setLayout(null);
		dialogPane.setBackground(Color.white);
		d.setBounds(700,100,620,520);          //���ô��ڵĴ�С
		label1.setText("*****************************���Ӵʵ�*****************************");
		label1.setFont(new Font("",1,20));   //�������
		label1.setBounds(25, 0, 620, 80);      //������ʾ��Ϣ��λ��
		label2.setText("���ʣ���/Ӣ����");
		label2.setBounds(25, 50, 100, 25);
		text=new JTextField();
		text.setBounds(25, 80, 400, 25);
		button1=new JButton("��ѯ");
		button1.addActionListener(this);
		button1.setBounds(450,80,100,25);
		button2=new JButton("�˳�");
		button2.addActionListener(this);
		button2.setBounds(480, 410, 100, 25);
		ta=new JTextArea();
		ta.setFont(new Font("",1,14));
		ta.setBorder(BorderFactory.createLineBorder(Color.black));
		ta.setLineWrap(true);
		jsp=new JScrollPane(ta);
		jsp.setBounds(25, 130, 550, 270);
		dialogPane.add(label1);
		dialogPane.add(label2);
		dialogPane.add(text);
		dialogPane.add(button1);
		dialogPane.add(button2);
		dialogPane.add(jsp);
		d.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd=e.getActionCommand();  //���������¼������ť������	
		try {
			//�����������������ڶ�ȡ���������͵���Ϣ
			sin=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			//�������������������������������Ϣ
			sout=new PrintWriter(clientSocket.getOutputStream());
			}catch (IOException e1) {
				e1.printStackTrace();
			}
		if(cmd.equals("��ѯ"))
		{
			String s=text.getText();
			ta.append(s+"\n");
			
			UseRSA rsa=new UseRSA();
			String Csig=Hash.md5(id+s);
			tax.append("Csig:"+Csig+"\n");
			Csig=rsa.encrypt(Csig, rsa.selfkey[0].toString(), rsa.selfkey[1].toString());
			s=s+" "+Csig;
			
			String[] s2=s.split("[\n \t\0]+");
			
			s=UseDES.encryMessage(s, key);
			s=Util.toBinaryString(s);
			sout.println(s);
			sout.flush();
			
			try {
				for(int i=0;i<s2.length-1;++i)
				{
					String s1=sin.readLine();
					ta.append(s1+"\n");
				}
			} catch (IOException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			}
		}
		if(cmd.equals("�˳�"))
		{
			tax.append("�Ự����\n");
			sout.println("~");
			sout.flush();
			d.dispose();
		}
	}
	/*public static void main(String[] args)
	{
		Socket clientSocket=null;
		try {
			clientSocket = new Socket("127.0.0.1",15421);
		} catch (UnknownHostException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		JTextArea a=new JTextArea();
		String key="";
		C_Search b=new C_Search(clientSocket,key,a);
		b.ini();	
	}*/
}
