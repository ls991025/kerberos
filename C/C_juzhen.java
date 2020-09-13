package C;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import AS.Hash;
import DES.UseDES;
import RSA.UseRSA;

public class C_juzhen implements ActionListener{
	Container dialogPane;
	JDialog d;
	JLabel label1;
	JLabel label2;
	JLabel label3;
	JLabel label4;
	JScrollPane jsp1;
	JScrollPane jsp2;
	JScrollPane jsp3;
	JTextField n;
	JTextArea xishu;
	JTextArea youzhi;
	JTextArea ta;
	JTextArea tax;
	JButton b1;
	JButton b2;
	
	String key;
	String id;
	Socket clientSocket = null;
	BufferedReader sin = null;
	PrintWriter sout = null;
	public C_juzhen(Socket clientSocket,String key,JTextArea tax,String id)
	{
		this.clientSocket=clientSocket;
		this.key=key;
		this.tax=tax;				//client������
		this.id=id;
	}
	public void ini(){
		d=new JDialog();                      //�½�һ�Ի���
		d.setTitle("�����");            //���ñ���
		dialogPane=d.getContentPane();
		dialogPane.setLayout(null);
		dialogPane.setBackground(Color.white);
		d.setBounds(700,100,620,550);          //���ô��ڵĴ�С
		label1=new JLabel();
		label2=new JLabel();
		label3=new JLabel();
		label4=new JLabel();
		label1.setText("����ά��n��");
		label2.setText("����a��");
		label3.setText("����b��");
		label4.setText("��˽�Ϊ��");
		label1.setBounds(10, 10, 100, 25);
		label2.setBounds(10, 40, 100, 25);
		label3.setBounds(400, 40, 100, 25);
		label4.setBounds(10, 220, 100, 25);
		n=new JTextField();
		n.setBounds(100, 10, 480, 25);
		xishu=new JTextArea();
		xishu.setFont(new Font("",1,14));
		youzhi=new JTextArea();
		youzhi.setFont(new Font("",1,14));
		ta=new JTextArea();
		jsp1=new JScrollPane(xishu);
		jsp1.setBounds(10, 70, 350, 150);
		jsp2=new JScrollPane(youzhi);
		jsp2.setBounds(400, 70, 180, 150);
		jsp3=new JScrollPane(ta);
		jsp3.setBounds(10, 250, 570, 150);
		b1=new JButton();
		b1.setText("����");
		b1.addActionListener(this);
		b2=new JButton();
		b2.setText("�˳�");
		b2.addActionListener(this);
		b1.setBounds(10, 410, 100, 25);
		b2.setBounds(480, 410, 100, 25);
		dialogPane.add(label1);
		dialogPane.add(label2);
		dialogPane.add(label3);
		dialogPane.add(label4);
		dialogPane.add(jsp1);
		dialogPane.add(jsp2);
		dialogPane.add(jsp3);
		dialogPane.add(n);
		dialogPane.add(b1);
		dialogPane.add(b2);
		d.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd=e.getActionCommand();  //���������¼������ť������	
		String s1,s2,s3;
		try {
			//�����������������ڶ�ȡ���������͵���Ϣ
			sin=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			//�������������������������������Ϣ
			sout=new PrintWriter(clientSocket.getOutputStream());
			}catch (IOException e1) {
				e1.printStackTrace();
			}
		if(cmd.equals("����"))
		{
			s1=n.getText();
			s2=xishu.getText();
			s3=youzhi.getText();
			if(s1.equals("")||s2.equals("")||s3.equals(""))	//������Ϊ�յ���ָʾ�Ի���
			{
				JDialog d1=new JDialog();                      //�½�һ�Ի���
				d1.setTitle("�����");            //���ñ���
				Container dialogPane1=d1.getContentPane();
				dialogPane1.setLayout(null);
				dialogPane1.setBackground(Color.white);
				d1.setBounds(600,280,300,200);          //���ô��ڵĴ�С
				JLabel label=new JLabel();
				if(s1.equals(""))
					label.setText("n ");
				if(s2.equals(""))
					label.setText(label.getText()+"A ");
				if(s3.equals(""))
					label.setText(label.getText()+"B ");
				label.setText(label.getText()+"����Ϊ�գ�");
				label.setFont(new Font("",1,16));
				label.setBounds(80, 50, 200, 50);
				dialogPane1.add(label);
				d1.setVisible(true);
			}
			else
			{	
				try {
					String ticket=s1+" "+s2+" "+s3;
					
					UseRSA rsa=new UseRSA();
					String Csig=Hash.md5(id+ticket);
					tax.append("Csig:"+Csig+"\n");
					Csig=rsa.encrypt(Csig, rsa.selfkey[0].toString(), rsa.selfkey[1].toString());
					
					ticket=ticket+" "+Csig;
					
					ticket=UseDES.encryMessage(ticket, key);
					String Final=ticket;
					Final = Util.toBinaryString(Final);
					sout.println(Final);
					sout.flush();
				
					int t=Integer.valueOf(s1);
					double result[][]=new double[t][t];
					for(int i=0;i<t;i++){    //���ռ�����
						for(int j=0;j<t;j++){
							result[i][j]=Double.valueOf(sin.readLine());
						}
					}
					ta.append("-----------------------------\n");
					for(int i=0;i<t;i++){    
						for(int j=0;j<t;j++){
							ta.append(result[i][j]+"  ");
						}
						ta.append("\n");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
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
		JTextArea a=new JTextArea();
		C_juzhen b=new C_juzhen(key,a);
		b.ini();	
	}*/
}

	
