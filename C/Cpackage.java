package C;

import java.util.Calendar;
import java.util.Random;



public class Cpackage {

	// 产生会话对称key
	public static String Createkey(){
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";   
		 Random random = new Random();   
		    StringBuffer sb = new StringBuffer();   
		    for (int i = 0; i < 8; i++) {   
		        int number = random.nextInt(base.length());   
		        sb.append(base.charAt(number));   
		    } 
		    return sb.toString(); 
	}
	
	//TGSID设为00000001
	public static String GetTGSID(){
		String TGSID = "00000001";
		return TGSID;
	}
	
	//lifetime 固定为1800s
	public static String GetLifetime(){
		String time = "1800";
		return time;
	}
	
	//获取时间戳
	public static String GetTS(){
		Calendar calendar = Calendar.getInstance();
		String timeStamp = String.valueOf(calendar.getTimeInMillis());
		return timeStamp;
	
	}

	//ip的点分十进制转换为12位字符串，用0填充
	public static String GetAD(String str){
		int h = str.length();
		String temp=str.substring(1, h);
		String s="";
		String []a=temp.split("\\.");
		for(int i = 0; i<4 ;i++){
			if(a[i].length()==1){
				a[i]="00"+a[i];
			}
			if(a[i].length()==2){
				a[i]="0"+a[i];
			}
			s+=a[i];
		}
		return s;
	}
	
	
	public static void main(String[] args){
        String temp ="/192.168.1.12";
        temp=GetAD(temp)+" "+Createkey();
		System.out.println(temp);
	}
}
