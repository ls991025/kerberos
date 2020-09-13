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

import RSA.UseRSA;

public class C_Login implements ActionListener{
	public static final int PORT=15421;
	Container dialogPane;
	JDialog d;
	JLabel label1=new JLabel();
	JLabel label2=new JLabel();
	JLabel label3=new JLabel();
	JTextField id;
	JTextField key;
	JTextArea ta;
	JScrollPane jsp;
	JButton sure;
	JButton end;
	String rsa_n;
	String rsa_e;

	
	public C_Login(String rsa_n,String rsa_e)
	{
		super();
		this.rsa_n=rsa_n;
		this.rsa_e=rsa_e;
		d=new JDialog(); 
		id=new JTextField();
		key=new JTextField();
		sure=new JButton("ȷ��");
		end =new JButton("����");
		d.setTitle("ע��");
		dialogPane=d.getContentPane();
		dialogPane.setLayout(null);
		d.setBounds(800,100,500,300);          //���ô��ڵĴ�С
		label1.setText("ID:");
		label1.setBounds(100, 5, 100, 30);      //������ʾ��Ϣ��λ��
		label2.setText("key:");
		label2.setBounds(100, 5, 100, 110);
		id.setBounds(150, 10, 200, 25); 
		key.setBounds(150, 50, 200, 25);
		sure.addActionListener((ActionListener) this);
		sure.setBounds(80,200,100,25);
		end.addActionListener(this);
		end.setBounds(280,200,100,25);
		ta=new JTextArea();
		ta.setFont(new Font("",1,14));
		ta.setBorder(BorderFactory.createLineBorder(Color.black));
		ta.setLineWrap(true);
		jsp=new JScrollPane(ta);
		jsp.setBounds(20, 90, 440, 100);
		dialogPane.add(label1);
		dialogPane.add(label2);
		dialogPane.add(id);
		dialogPane.add(key);
		dialogPane.add(sure);
		dialogPane.add(end);
		dialogPane.add(jsp);
		d.setVisible(true);
	}
	public String getID()
	{
		return id.getText();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd=e.getActionCommand();
		if(cmd.equals("ȷ��")){
			String Server="127.0.0.1";
			Socket clientSocket;
			BufferedReader sin = null;
			PrintWriter sout = null;

			try {
				clientSocket=new Socket(Server,PORT);
			//�����������������ڶ�ȡ���������͵���Ϣ
				sin=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			//�������������������������������Ϣ
				sout=new PrintWriter(clientSocket.getOutputStream());
				ta.append("���ӳɹ�\n");
			} catch (IOException e1) {
				ta.append(e1.getMessage());
			}
			
				//���˺��������hash
				String ID=Hash.md5(id.getText());
				String KEY=Hash.md5(key.getText());
				
				String User=ID+KEY;
				UseRSA rsa=new UseRSA();
				User=rsa.encrypt(User, rsa_n, rsa_e);	//��Կ�����˺������hash
				String Final="@"+User;		//'@'��������һ��ע�ᱨ��
				ta.append(Final+"\n");
				sout.println(Final);
				sout.flush();
			
				try {
					String s=sin.readLine();
					if(s.equals("1"))
						ta.append("ע��ɹ���\n");
					if(s.equals("0"))
						ta.append("�û�����ʹ��,ע��ʧ��\n");
				} catch (IOException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
			
		}	
		if(cmd.equals("����")){
			d.dispose();
		}
	}
}

