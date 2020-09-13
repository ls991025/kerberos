package AS;
import java.sql.*;

public class linksql {	
	/**
	 * AS连接数据库并注册用户
	 * @param username   新用户名
	 * @param cipher     密码
	 */
	public void Linksql(String username,String cipher){
		String driver = "com.mysql.cj.jdbc.Driver";
		//URL指向要访问的数据库名word
		String url = "jdbc:mysql://localhost:3306/test?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true";
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
			     String sql =  "insert into user(name,password) values('"+username+"','"+cipher+"')";
			     //String sql =  "INSERT INTO user(name,password) VALUES()";
			     //PreparedStatement ptmt = conn.prepareStatement(sql);
			     //ptmt.setString(1,username);
			     //ptmt.setString(2,cipher);
			     int rs = statement.executeUpdate(sql);
			     //ptmt.execute();
			//ptmt.close();
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
	 * AS认证函数
	 * @param username   用户名
	 * @param cipher     密码
	 * @return 1 为认证成功     0为认证失败
	 */
	public int Authen(String username){
		String driver = "com.mysql.cj.jdbc.Driver";
		//URL指向要访问的数据库名word
		String url = "jdbc:mysql://localhost:3306/test?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
		String user = "root";
		String password = "ls991025";
		Connection conn = null;
		String name = null;
		int h;
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url,user,password);
			if(!conn.isClosed())
				 h=0;
			     Statement statement = conn.createStatement();
			     String sql =  "select * from user where name ='"+username+"'";
			     ResultSet rs = statement.executeQuery(sql);
			     while(rs.next())  {
			    	 name= rs.getString("name");
			    	 System.out.println(name);
			    	 if(name.equals(username)){
			    		//System.out.println(username + "认证成功！");
			    		return 1;
			    	 }else{
			    		 //System.out.println(username + "认证失败！");
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



public String GetUserPassword(String username){
	String driver = "com.mysql.cj.jdbc.Driver";
	//URL指向要访问的数据库名word
	String url = "jdbc:mysql://localhost:3306/test?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
	String user = "root";
	String password = "ls991025";
	Connection conn = null;
	String pwd = null;
	int h;
	try{
		Class.forName(driver);
		conn = DriverManager.getConnection(url,user,password);
		if(!conn.isClosed())
			 h=0;
		     Statement statement = conn.createStatement();
		     String sql =  "select * from user where name ='"+username+"'";
		     ResultSet rs = statement.executeQuery(sql);
		     while(rs.next())  {
		    	 pwd= rs.getString("password");
		    	 String name=rs.getString("name");
		    	 System.out.println(pwd);
		    	 if(name.equals(username)){
		    		//System.out.println(username + "认证成功！");
		    		return pwd;
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
