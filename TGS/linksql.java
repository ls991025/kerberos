package TGS;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class linksql {	
	/**
	 * AS�������ݿⲢע���û�
	 * @param username   ���û���
	 * @param cipher     ����
	 */
	public void Linksql(String ID,String IP){
		String driver = "com.mysql.cj.jdbc.Driver";
		//URLָ��Ҫ���ʵ����ݿ���word
		String url = "jdbc:mysql://localhost:3306/test?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
		String user = "root";
		String password = "ls991025";
		Connection conn = null;
		int h;
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url,user,password);
			if(!conn.isClosed())
				 h=0;
			     Statement statement = conn.createStatement();
			     String sql =  "insert into V(ID,IP) values('"+ID+"','"+IP+"')";
			     int rs = statement.executeUpdate(sql);      
		}catch(ClassNotFoundException e){
			e.printStackTrace();  
		}catch(SQLException e){
			e.printStackTrace();  
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	
	}
	
	/**
	 * AS��֤����
	 * @param username   �û���
	 * @param cipher     ����
	 * @return 1 Ϊ��֤�ɹ�     0Ϊ��֤ʧ��
	 */
	public int Authen(String ID){
		String driver = "com.mysql.cj.jdbc.Driver";
		//URLָ��Ҫ���ʵ����ݿ���word
		String url = "jdbc:mysql://localhost:3306/test?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
		String user = "root";
		String password = "ls991025";
		Connection conn = null;
		String id = null;
		int h;
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url,user,password);
			if(!conn.isClosed())
				 h=0;
			     Statement statement = conn.createStatement();
			     String sql =  "select * from V where ID ='"+ID+"'";
			     ResultSet rs = statement.executeQuery(sql);
			     while(rs.next())  {
			    	 id= rs.getString("ID");
			    	 System.out.println(id);
			    	 if(id.equals(ID)){
			    		//System.out.println(username + "��֤�ɹ���");
			    		return 1;
			    	 }else{
			    		 //System.out.println(username + "��֤ʧ�ܣ�");
			    		 return 0;
			    	 }
			    	 
			     }
			     rs.close();       
			      
		}catch(ClassNotFoundException e){	
			e.printStackTrace();  
		}catch(SQLException e){
			e.printStackTrace();  
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		return 0;
	
	}



public String GetIP(String ID){
	String driver = "com.mysql.cj.jdbc.Driver";
	//URLָ��Ҫ���ʵ����ݿ���word
	String url = "jdbc:mysql://localhost:3306/test?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
	String user = "root";
	String password = "ls991025";
	Connection conn = null;
	String ip = null;
	int h;
	try{
		Class.forName(driver);
		conn = DriverManager.getConnection(url,user,password);
		if(!conn.isClosed())
			 h=0;
		     Statement statement = conn.createStatement();
		     String sql =  "select * from V where ID ='"+ID+"'";
		     ResultSet rs = statement.executeQuery(sql);
		     while(rs.next())  {
		    	 ip= rs.getString("IP");
		    	 String id=rs.getString("ID");
		    	 System.out.println(ip);
		    	 if(id.equals(ID)){
		    		//System.out.println(username + "��֤�ɹ���");
		    		return ip;
		    	 }
		     }
		     rs.close();       
		      
	}catch(ClassNotFoundException e){		
		e.printStackTrace();  
	}catch(SQLException e){
		e.printStackTrace();  
	}catch(Exception e){
		e.printStackTrace();
	}
	finally{
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	return null;

	}

}
