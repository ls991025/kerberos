package TGS;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TGSui implements ActionListener{  	 
	Container dialogPane;
	JFrame d;
	JLabel label1=new JLabel();
	JButton button1;
	JButton button2;
	JTextArea ta;
	JScrollPane jsp;
    public void ini(){ 
    	d=new JFrame();                      //新建一对话框
		d.setTitle("TGS服务器");            //设置标题
		dialogPane=d.getContentPane();
		dialogPane.setLayout(null);
		dialogPane.setBackground(Color.white);
		d.setBounds(700,100,620,500);          //设置窗口的大小
		label1.setText("*****************************TGS服务*****************************");
		label1.setFont(new Font("",1,20));   //字体设计
		label1.setBounds(25, 1, 620, 80);      //设置显示信息的位置
		button1=new JButton("注册V");
		button1.addActionListener(this);
		button1.setBounds(55,400,110,25);
		button2=new JButton("退出");
		button2.addActionListener(this);
		button2.setBounds(425,400,110,25);
		ta=new JTextArea();
		ta.setFont(new Font("",1,16));
		ta.setBorder(BorderFactory.createLineBorder(Color.black));
		ta.setLineWrap(true);
		jsp=new JScrollPane(ta);
		jsp.setBounds(25, 70, 550, 300);
		dialogPane.add(label1);
		dialogPane.add(button1);
		dialogPane.add(button2);
		dialogPane.add(jsp);
		d.setVisible(true);
    	}
    public void actionPerformed(ActionEvent e) {
    		String cmd=e.getActionCommand();
    		if(cmd.equals("注册V"))
    		{
    			new VLogin();
    		}
    		if(cmd.equals("退出")){
    			d.dispose();
    			System.exit(0);
    		}
    	}
}