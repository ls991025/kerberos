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
		sure=new JButton("确定");
		end =new JButton("返回");
		d.setTitle("注册");
		dialogPane=d.getContentPane();
		dialogPane.setLayout(null);
		d.setBounds(800,100,500,300);          //设置窗口的大小
		label1.setText("ID:");
		label1.setBounds(100, 5, 100, 30);      //设置显示信息的位置
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
		if(cmd.equals("确定")){
			String Server="127.0.0.1";
			Socket clientSocket;
			BufferedReader sin = null;
			PrintWriter sout = null;

			try {
				clientSocket=new Socket(Server,PORT);
			//创建缓冲区对象，用于读取服务器发送的信息
				sin=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			//创建缓冲区对象，用于向服务器发送信息
				sout=new PrintWriter(clientSocket.getOutputStream());
				ta.append("连接成功\n");
			} catch (IOException e1) {
				ta.append(e1.getMessage());
			}
			
				//对账号密码进行hash
				String ID=Hash.md5(id.getText());
				String KEY=Hash.md5(key.getText());
				
				String User=ID+KEY;
				UseRSA rsa=new UseRSA();
				User=rsa.encrypt(User, rsa_n, rsa_e);	//公钥加密账号密码的hash
				String Final="@"+User;		//'@'标明这是一个注册报文
				ta.append(Final+"\n");
				sout.println(Final);
				sout.flush();
			
				try {
					String s=sin.readLine();
					if(s.equals("1"))
						ta.append("注册成功！\n");
					if(s.equals("0"))
						ta.append("用户名已使用,注册失败\n");
				} catch (IOException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			
		}	
		if(cmd.equals("返回")){
			d.dispose();
		}
	}
}

