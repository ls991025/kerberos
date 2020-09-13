package C;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client implements ActionListener{
	Container dialogPane;
	JFrame d;
	JLabel label1=new JLabel();
	JLabel label2=new JLabel();
	JComboBox<String> jc;
	JButton button1;
	JButton button2;
	String idv;
	JScrollPane jsp;
	public Client(){
		super();		
		String[] s={"","矩阵乘法","电子词典"};
		d=new JFrame();                      //新建一对话框
		d.setTitle("Client");            //设置标题
		dialogPane=d.getContentPane();
		dialogPane.setLayout(null);
		dialogPane.setBackground(Color.white);
		d.setBounds(800,100,450,400);          //设置窗口的大小
		label1.setText("               应用服务选择               ");
		label1.setFont(new Font("",1,20));   //字体设计
		label1.setBounds(50, 0, 400, 120);      //设置显示信息的位置	
		label2.setText("请选择应用服务：");
		label2.setBounds(70, 50, 400, 120);	
		jc=new JComboBox<String>(s);
		jc.setBounds(200, 100, 110, 25);
		button1=new JButton("确定");
		button1.addActionListener(this);
		button1.setBounds(70,250,110,25);
		button2=new JButton("退出");
		button2.addActionListener(this);
		button2.setBounds(250,250,110,25);
		dialogPane.add(label1);
		dialogPane.add(label2);
		dialogPane.add(jc);
		dialogPane.add(button1);
		dialogPane.add(button2);
		d.setVisible(true);
	}
	//按钮点击事件
		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd=e.getActionCommand();
			if(cmd.equals("确定")){
				if(jc.getSelectedItem().equals(""))
				{
					JDialog d1=new JDialog();                      //新建一对话框
					d1.setTitle("Client");            //设置标题
					Container dialogPane1=d1.getContentPane();
					dialogPane1.setLayout(null);
					dialogPane1.setBackground(Color.white);
					d1.setBounds(800,400,300,200);          //设置窗口的大小
					JLabel label=new JLabel();
					label.setText("应用框不能为空！");
					label.setFont(new Font("",1,16));
					label.setBounds(70, 50, 200, 50);
					dialogPane1.add(label);
					d1.setVisible(true);
				}
				else
				{
					if(jc.getSelectedItem().equals("矩阵乘法"))
					{
						idv="20171001";
					}
					if(jc.getSelectedItem().equals("电子词典"))
					{
						idv="20171002";
					}
					/*if(jc.getSelectedItem().equals("人物百科"))
					{
						idv="20173";
					}
					if(jc.getSelectedItem().equals("Server4"))
					{
						idv="20174";
					}*/
					d.dispose();
					
					new Authenticator(idv,d);
				}	
			}
			if(cmd.equals("退出")){
				d.dispose();
				System.exit(0);
			}
		}
	public static void main(String[] args)
	{
		Client client1=new Client();
		//client1.run();
		//Client client2=new Client();
		//client2.run();
	}
}
