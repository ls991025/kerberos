package V;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;

import javax.swing.JTextArea;

import DES.UseDES;
import RSA.UseRSA;

public class Vrunnable implements Runnable{
	int n=0;
	String keycv;
	String CID="";
	Socket client;
	JTextArea text=null;
	BufferedReader cin = null;
	PrintWriter cout;
	public Vrunnable(){
		
	}
	public Vrunnable(Socket client,JTextArea text)
	{
		this.client=client;
		this.text = text;
	}
	@Override
	public void run() {
		try{
			//创建缓冲区对象，用于读取客户端发送的信息
			cin=new BufferedReader(new InputStreamReader(client.getInputStream()));
			//创建缓冲区对象，用于向客户端发送信息
			cout=new PrintWriter(client.getOutputStream());

			String s = cin.readLine();

			s=Util.binaryToString(s);
			text.append("收到:"+s+"\n");
				
			DataPacket dataPacket = new DataPacket();
			dataPacket.parseMessage(s, dataPacket);

			//认证id和timestamp
			text.append("Tick_cid"+dataPacket.getTicket().getSrc_id()+"\n");
			text.append("Au_cid"+dataPacket.getAuthenticator().getSrc_id()+"\n");
			text.append("Tick_ts"+dataPacket.getTicket().getTimeStamp()+"\n");
			text.append("Au_ts"+dataPacket.getAuthenticator().getTimeStamp()+"\n");
			if(dataPacket.getTicket().getSrc_id().equals(dataPacket.getAuthenticator().getSrc_id())
			&&dataPacket.getTicket().getTimeStamp().equals(dataPacket.getAuthenticator().getTimeStamp()))
			{
				String head="0001000100000000";
				CID=dataPacket.getAuthenticator().getSrc_id();
				String timeStamp=dataPacket.getTicket().getTimeStamp();
				BigInteger b = new BigInteger(timeStamp).add(new BigInteger("1"));//timestamp+1
				timeStamp = b.toString();
				keycv=dataPacket.getTicket().getKey();
				text.append("ts+kcv:"+timeStamp+keycv+"\n");
				String Finaltext=head+UseDES.encryMessage(timeStamp, keycv);
				text.append("Final:"+Finaltext+"\n");
				Finaltext=Util.toBinaryString(Finaltext);
				cout.println(Finaltext);
				cout.flush();
			}else{
				String str = dataPacket.getTicket().getSrc_id();
				text.append("用户："+str+"认证失败！\n");
				String e=Util.toBinaryString("0001000000000000");
				cout.println(e);
				cout.flush();
				return;
			}
			
			//client server交互
			text.append(dataPacket.getTicket().getDes_id()+"\n");
			boolean f=true;					//vid错误时结束线程释放资源
			while(f)
			{
				s = cin.readLine();
				text.append("V:收到："+s+"\n");
				if(s.charAt(0)=='~')
				{
					text.append("用户:"+CID+" shutdown\n");
					break;
				}
				switch(dataPacket.getTicket().getDes_id())
				{
				case "20171001":
					V_juzhen(s);
					break;
				case "20171002":
					Search(s);
					break;
				case "20171003":
					GetInfo(s);
					break;
				case "20171004":
					text.append("server4:\n");
					break;
				default:
					text.append("vid错误\n");
					f=false;						//线程结束释放资源
					break;
				}						
			}	
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void V_juzhen(String s)
	{
		text.append("矩阵乘server:\n");
		s=Util.binaryToString(s);
		s=UseDES.decryMessage(s, keycv);
		
		
		
		String[] string=s.split("[\n \t\0]+");
		
		n=Integer.valueOf(string[0]);
		
		String Csig=string[n*n*2+1];
		//text.append("Csig:"+Csig+"\n");
		UseRSA rsa=new UseRSA();
		Csig=rsa.decrypt(Csig,rsa.pubkey[0].toString(), rsa.pubkey[1].toString());
		text.append("Csig:"+Csig+"\n");
		int k=1;
		double A[][]=new double[n][n];double B[][]=new double[n][n];
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
			{
				A[i][j]=Double.valueOf(string[k]);
				B[i][j]=Double.valueOf(string[k+n*n]);
				k++;
			}
			
		double C[][]=new double[n][n];
		C=Calculate(A,B);       //矩阵计算
		for(int i=0;i<n;i++){    //将计算结果传给客户机
			for(int j=0;j<n;j++){
				cout.println(String.valueOf(C[i][j]));
				cout.flush();
				text.append(String.valueOf(C[i][j])+" ");
			}
			text.append("\n");
		}
	}
	
	public double[][] Calculate(double A[][],double B[][]){     //矩阵计算函数
		int a=n;int b=n;
		double C[][]=new double[a][b];
		for(int i=0;i<a;i++){
			for(int j=0;j<b;j++){
				for(int k=0;k<b;k++){
					C[i][j]+=A[i][k]*B[k][j];
				}
			}
		}
		return C;
	}
	
	
	public void Search(String word) {//查找单词
		word=Util.binaryToString(word);
		word=UseDES.decryMessage(word, keycv);
		text.append("查单词server:\n");
		String[] w=word.split("[\n \t\0]+");
		
		String Csig=w[1];
		//text.append("Csig:"+Csig+"\n");
		UseRSA rsa=new UseRSA();
		Csig=rsa.decrypt(Csig,rsa.pubkey[0].toString(), rsa.pubkey[1].toString());
		text.append("Csig:"+Csig+"\n");
		
		String ans="抱歉，没找到该单词!";
		FileReader read=null;
        String str=null;
        BufferedReader br=null;
        
		for(int i=0;i<w.length-1;++i)
		{
			try {
            read=new FileReader("资源文件/单词");
 	        br = new BufferedReader(read);
	        while ((str = br.readLine()) != null) {
		         String str2[]=str.split(" +",2);
		         if(w[i].equals(str2[0])) {
		        	 ans=str2[1];
		        	 break;
		        }
		         else if(w[i].equals(str2[1])) {
		        	 ans=str2[0];
		        	 break;
		         }
	        }
	        br.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		cout.println(ans);
		cout.flush();
		text.append(ans+" ");
		text.append("\n");
		}
		
   }
	public void GetInfo(String name) {//获取文件信息
		String[] n=name.split("[\n \t\0]+");
		
		text.append("用户简介:\n");
		FileReader read=null;
        
        BufferedReader br=null;
        for(int i=0;i<n.length;++i)
		{
        	String ans=n[i]+"\n";
        	String str="";
        	if(n[i].equals("鲁迅")) {
        		try {
        			read=new FileReader("资源文件/鲁迅");
        			br = new BufferedReader(read);
        			while ((str = br.readLine()) != null) {
        				ans+=str+'\n';
        			}
        		}
        		catch(Exception e) {
        			e.printStackTrace();
        		}
        		try {
        			br.close();
        		}
        		catch(Exception e) {
        			System.out.println("文件关闭出错!");
        		}
        	}
        	else if(n[i].equals("胡歌")) {
        		try {
        			read=new FileReader("资源文件/胡歌");
        			br = new BufferedReader(read);
        			while ((str = br.readLine()) != null) {
        				ans+=str+'\n';
        			}
        		}
        		catch(Exception e) {
        			e.printStackTrace();
        		}
        		try {
        			br.close();
        		}
        		catch(Exception e) {
        			System.out.println("文件关闭出错!");
        		}
        	}
        	else if(n[i].equals("李白")) {
        		try {
        			read=new FileReader("资源文件/李白");
        			br = new BufferedReader(read);
        			while ((str = br.readLine()) != null) {
        				ans+=str+'\n';
        			}
        		}
        		catch(Exception e) {
        			e.printStackTrace();
        		}
        		try {
        			br.close();
        		}
        		catch(Exception e) {
        			System.out.println("文件关闭出错!");
        		}
        	}else
        	{
        		ans="查无此项！";
        	}
        	cout.write(ans);
        	cout.flush();
        	text.append(ans+" ");
        	text.append("\n");
		}
        cout.println("#");
        cout.flush();
	}  
}