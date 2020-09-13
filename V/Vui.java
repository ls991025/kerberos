package V;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class Vui implements ActionListener{  	 
	Container dialogPane;
	JFrame d;
	JLabel label1=new JLabel();
	JButton button;
	JTextArea ta;
	JScrollPane jsp;
    public void ini(){ 
    	d=new JFrame();                      //新建一对话框
		d.setTitle("Server");            //设置标题
		dialogPane=d.getContentPane();
		dialogPane.setLayout(null);
		dialogPane.setBackground(Color.white);
		d.setBounds(700,100,620,500);          //设置窗口的大小
		label1.setText("*****************************Server*****************************");
		label1.setFont(new Font("",1,20));   //字体设计
		label1.setBounds(25, 1, 620, 80);      //设置显示信息的位置		

		button=new JButton("退出");
		button.addActionListener(this);
		button.setBounds(425,400,110,25);
		ta=new JTextArea();
		ta.setFont(new Font("",1,16));
		ta.setBorder(BorderFactory.createLineBorder(Color.black));
		ta.setLineWrap(true);
		jsp=new JScrollPane(ta);
		jsp.setBounds(25, 70, 550, 300);
		dialogPane.add(label1);
		dialogPane.add(button);
		dialogPane.add(jsp);
		d.setVisible(true);
    	}
    public void actionPerformed(ActionEvent e) {
    		String cmd=e.getActionCommand();
    		if(cmd.equals("退出")){
    			d.dispose();
    			System.exit(0);
    		}
    	}
}