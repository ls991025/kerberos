package AS;
import java.util.Calendar;
import java.util.Random;

public class ASpackage {
  /**
   * Kc,tgs 
   * @return  8位字符串
   */
	public static String Createkey(int length){
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";   
		 Random random = new Random();   
		    StringBuffer sb = new StringBuffer();   
		    for (int i = 0; i < length; i++) {   
		        int number = random.nextInt(base.length());   
		        sb.append(base.charAt(number));   
		    }   
		    return sb.toString(); 
	}
	
	/**
	 * TS
	 * 
	 * @return
	 */
	public static String Gettimestamp(){
		Calendar calendar = Calendar.getInstance();
		String timeStamp = String.valueOf(calendar.getTimeInMillis());
		return timeStamp;
	
	}
	
	
	public static String  Lifetime(){
		String lifetime = "1800";
		return lifetime;
	}
	
	/**
	 * 需要Socket输出流  AD源IP
	 * @return
	 */
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
	String key = Createkey(8);
	String timestamp = Gettimestamp();
	System.out.println(key+" "+timestamp);
	}
}
